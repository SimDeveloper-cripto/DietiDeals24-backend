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

    private final String GENTILE = "Gentile";
    private final String INFO = ", la informiamo che la sua asta per l'Item ";

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
            User owner = null;
            Optional<User> retrievedOwner = userRepository.findById(auction.getOwnerId());
            if (retrievedOwner.isPresent()) owner = retrievedOwner.get();

            assert owner != null;
            Integer ownerId   = owner.getUserId();
            Integer auctionId = auction.getAuctionId();

            auction.updateStatusForSilentAuction();
            auctionRepository.save(auction);

            // Checking the TOP record, we assure that at least there is one offer for that auctioned Item
            Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
            if (retrievedOffer.isPresent()) {
                List<Offer> offers = offerRepository.findOffers(auction.getItem().getItemId(), auction.getAuctionId());

                // For each Auction, we store the list of offers
                for (Offer o : offers) {
                    OfferDTO offerDTO = new OfferDTO();
                    offerDTO.setOfferId(o.getOfferId());
                    offerDTO.setUser(o.getUser());
                    offerDTO.setOffer(o.getOffer());
                    offerDTO.setAuctionId(auctionId);
                    offerDTO.setAuctionType(o.getAuction().getAuctionType());

                    // Date and Time are not relevant to set in this case
                    notificationService.addOfferToMap(auction.getItem().getItemId(), offerDTO);
                }

                // Notifications for each User
                for (Offer o : offers) {
                    User user = null;
                    Optional<User> retrievedUser = userRepository.findById(o.getUser().getUserId());
                    if (retrievedUser.isPresent()) user = retrievedUser.get();

                    assert user != null;
                    String message = GENTILE + " " + user.getName() + " " + user.getSurname() + INFO + auction.getItem().getName() + " è terminata!";
                    notificationService.addSilentAuctionNotificationForUser(user.getUserId(), message);
                }

                String warningMessage = GENTILE + " " + owner.getName() + " " + owner.getSurname() + INFO + auction.getItem().getName();
                notificationService.addSilentAuctionNotificationForUser(ownerId, warningMessage);
            } else {
                String auctionerNofificationMessage = GENTILE + " " + owner.getName() + " " + owner.getSurname() + INFO + auction.getItem().getName() + " è terminata senza offerte!";
                notificationService.addSilentAuctionNotificationForUser(ownerId, auctionerNofificationMessage);
            }
        }
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
        String auctionerNofificationMessage = GENTILE + " " + owner.getName() + " " + owner.getSurname() + INFO + auction.getItem().getName() + " è terminata con successo!";
        notificationService.addSilentAuctionNotificationForUser(owner.getUserId(), auctionerNofificationMessage);

        notificationService.clearOfferMap(auction.getItem().getItemId());
    }
}