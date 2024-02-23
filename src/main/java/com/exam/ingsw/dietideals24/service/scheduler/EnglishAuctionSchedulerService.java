package com.exam.ingsw.dietideals24.service.scheduler;

import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.enums.Type;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.model.Auction;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import com.exam.ingsw.dietideals24.repository.IUserRepository;
import com.exam.ingsw.dietideals24.repository.IOfferRepository;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;

/* [DESCRIPTION]
    - It is used to check the expired english auctions once when the application is started and every minute, updating them if necessary.
**/

public class EnglishAuctionSchedulerService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOfferRepository offerRepository;
    @Autowired
    private IAuctionRepository auctionRepository;
    @Autowired
    private AuctionNotificationService notificationService;

    /* [DESCRIPTION]
        - Execute once at startup
        - Execute once a minute
    **/
    @Scheduled(cron = "0 * * * * ?")
    @EventListener(ApplicationReadyEvent.class)
    public void checkForExpiredEnglishAuctions() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> expiredEnglishAuctions = auctionRepository.findByAuctionTypeAndActiveIsTrueAndExpirationTimeBefore(Type.ENGLISH, now);

        for (Auction auction : expiredEnglishAuctions) {
            auction.setActive(false);
            auctionRepository.save(auction);

            // Prepare notifications
            addToNotificationList(auction);
        }
    }

    private void addToNotificationList(Auction auction) {
        Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
        if (retrievedOffer.isPresent()) { // We assure to have at least one offer
            Offer offer = retrievedOffer.get();
            User winner = offer.getUser();

            // Save:
            // 1. Winner Information
            // 2. Winning bid Information
            auction.setWinnerId(winner.getUserId());
            auction.setWinningBid(offer.getOffer());
            auctionRepository.save(auction);

            String notificationMessage = "Congratulazioni " + offer.getUser().getName() + "! Hai vinto l'asta per " + auction.getItem().getName() + ".";
            notificationService.addEnglishAuctionNotificationForUser(winner.getUserId(), notificationMessage);

            List<Offer> offers = offerRepository.findOffersWhereUserIdIsNotWinner();

            /*
                - It could happen that there is only one bidder (that also happens to be a winner). Even in this case everything is fine
            **/
            if (!offers.isEmpty()) {
                for (Offer o : offers) {
                    // For each bidder record whose userId is different from the winnerId, ADD AUCTION END NOTIFICATION
                    String message = "Gentile " + o.getUser().getName() + " " + o.getUser().getSurname() +
                            ", la informiamo che l'asta per l'Item " + auction.getItem().getName() + " è terminata!";
                    notificationService.addEnglishAuctionNotificationForUser(o.getUser().getUserId(), message);
                }
            }
        } else {
            // If there are no offers, just notify the owner of the Auction
            User owner = null;
            Optional<User> retrievedOwner = userRepository.findById(auction.getOwnerId());
            if (retrievedOwner.isPresent()) owner = retrievedOwner.get();

            assert owner != null;
            String auctionerNofificationMessage = "Gentile " + owner.getName() + " " + owner.getSurname() +
                    ", la informiamo che la sua asta per l'Item " + auction.getItem().getName() + " è terminata senza offerte!";
            notificationService.addEnglishAuctionNotificationForUser(owner.getUserId(), auctionerNofificationMessage);
        }
    }
}