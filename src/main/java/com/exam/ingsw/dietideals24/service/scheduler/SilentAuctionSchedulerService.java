package com.exam.ingsw.dietideals24.service.scheduler;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.model.dto.OfferDTO;
import com.exam.ingsw.dietideals24.repository.IUserRepository;
import com.exam.ingsw.dietideals24.repository.IOfferRepository;

import com.exam.ingsw.dietideals24.enums.Type;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;

/* [DESCRIPTION]
    - It is used to check the expired silent auctions once when the application is started and every midnight and update them if necessary.
**/

@Service
public class SilentAuctionSchedulerService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOfferRepository offerRepository;
    @Autowired
    private IAuctionRepository auctionRepository;
    @Autowired
    private AuctionNotificationService notificationService;

    private static final String gentile = "Gentile";
    private static final String info = ", la informiamo che la sua asta per l'Item ";

    @PostConstruct
    public void checkForExpiredSilentAuctionsOnStartUp() {
        checkForExpiredSilentAuctions();
    }

    /* [DESCRIPTION]
        - Execute once at startup
        - Execute every day at midnight
        - Execute once ended a silent auction
    **/
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkForExpiredSilentAuctions() {
        List<Auction> expiredSilentAuctions = auctionRepository.findByAuctionTypeAndActiveIsTrueAndExpirationDateBefore(Type.SILENT, new Date(System.currentTimeMillis()));
        for (Auction auction : expiredSilentAuctions) {
            processAuction(auction);
        }
    }

    private void processAuction(Auction auction) {
        User owner = getUser(auction.getOwnerId());

        assert owner != null;
        auction.updateStatusForSilentAuction();
        auctionRepository.save(auction);

        Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
        if (retrievedOffer.isPresent()) {
            processOffersForAuction(auction, owner);
        } else {
            notifyAuctionWithoutOffers(auction, owner);
        }
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private void processOffersForAuction(Auction auction, User owner) {
        List<Offer> offers = offerRepository.findOffers(auction.getItem().getItemId(), auction.getAuctionId());
        offers.forEach(o -> processOffer(auction, o));
        notifyUsersForAuctionEnd(auction, offers, owner);
    }

    private void processOffer(Auction auction, Offer offer) {
        OfferDTO offerDTO = mapOfferToDTO(auction, offer);
        notificationService.addOfferToMap(auction.getItem().getItemId(), offerDTO);
    }

    private OfferDTO mapOfferToDTO(Auction auction, Offer offer) {
        OfferDTO offerDTO = new OfferDTO();
        offerDTO.setOfferId(offer.getOfferId());
        offerDTO.setUser(offer.getUser());
        offerDTO.setOffer(offer.getOffer());
        offerDTO.setAuctionId(auction.getAuctionId());
        offerDTO.setAuctionType(offer.getAuction().getAuctionType());
        return offerDTO;
    }

    private void notifyUsersForAuctionEnd(Auction auction, List<Offer> offers, User owner) {
        offers.forEach(o -> {
            User user = getUser(o.getUser().getUserId());
            assert user != null;
            sendNotificationToUser(auction, user, false);
        });
        sendNotificationToUser(auction, owner, true);
    }

    private void sendNotificationToUser(Auction auction, User user, boolean isOwner) {
        String message = (isOwner ? "" : gentile) + user.getName() + " " + user.getSurname() + info + auction.getItem().getName() + (isOwner ? " è terminata senza offerte!" : " è terminata!");
        notificationService.addSilentAuctionNotificationForUser(user.getUserId(), message);
    }

    private void notifyAuctionWithoutOffers(Auction auction, User owner) {
        sendNotificationToUser(auction, owner, true);
    }

    public void notifyExpiredSilentAuctionForUser(Integer auctionId, Integer userId, Float winningBid) {
        User winner = null;
        Optional<User> retrievedWinner = userRepository.findById(userId);
        if (retrievedWinner.isPresent()) winner = retrievedWinner.get();

        Auction auction = null;
        Optional<Auction> retrievedAuction = auctionRepository.findById(auctionId);
        if (retrievedAuction.isPresent()) auction = retrievedAuction.get();

        assert winner != null;
        assert auction != null;

        // Save:
        // 1. Winner Information
        // 2. Winning bid Information
        auction.setWinnerId(winner.getUserId());
        auction.setWinningBid(winningBid);
        auctionRepository.save(auction);

        String winnerNotificationMessage = "Congratulazioni " + winner.getName() + "! Hai vinto l'asta per " + auction.getItem().getName() + ".";
        notificationService.addSilentAuctionNotificationForUser(winner.getUserId(), winnerNotificationMessage);

        User owner = null;
        Optional<User> retrievedOwner = userRepository.findById(auction.getOwnerId());
        if (retrievedOwner.isPresent()) owner = retrievedOwner.get();

        assert owner != null;
        String auctionerNofificationMessage = gentile + " " + owner.getName() + " " + owner.getSurname() + info + auction.getItem().getName() + " è terminata con successo!";
        notificationService.addSilentAuctionNotificationForUser(owner.getUserId(), auctionerNofificationMessage);

        notificationService.clearOfferMap(auction.getItem().getItemId());
    }
}