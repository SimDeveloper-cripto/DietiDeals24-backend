package com.exam.ingsw.dietideals24.service.scheduler;

import java.sql.Date;
import java.util.Optional;
import java.time.LocalDateTime;

import java.util.List;
import java.util.ArrayList;

import jakarta.annotation.PostConstruct;
import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.repository.IUserRepository;
import com.exam.ingsw.dietideals24.repository.IOfferRepository;

import com.exam.ingsw.dietideals24.enums.Type;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;

import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

/* [DESCRIPTION]
    - It is used to check the expired silent auctions once when the application is started and every midnight and update them if necessary.
**/

@Service
public class AuctionSchedulerService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IOfferRepository offerRepository;
    @Autowired
    private IAuctionRepository auctionRepository;

    private final List<String> pendingNotifications = new ArrayList<>();
    @Autowired
    private AuctionNotificationService notificationService;

    @PostConstruct
    public void checkForExpiredSilentAuctionsOnStartUp() {
        checkForExpiredSilentAuctions();
    }

    /* [DESCRIPTION]
        - Execute once at startup
        - Execute every day at midnight
        - Execute once ended a silent auction
    **/
    // TODO: (SILENT AUCTION) FOR NOW THIS FUNCTIONS NOTIFIES ONLY WINNERS
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkForExpiredSilentAuctions() {
        List<Auction> expiredSilentAuctions = auctionRepository.findByAuctionTypeAndActiveIsTrueAndExpirationDateBefore(Type.SILENT, new Date(System.currentTimeMillis()));

        for (Auction auction : expiredSilentAuctions) {
            auction.updateStatusForSilentAuction();
            auctionRepository.save(auction);
            this.addToNotificationList(auction);
        }
    }

    /* [DESCRIPTION]
        - Execute once at startup
        - Execute once a minute
    **/
    // TODO: (ENGLISH AUCTION) FOR NOW THIS FUNCTIONS NOTIFIES ONLY WINNERS
    @Scheduled(cron = "0 * * * * ?")
    @EventListener(ApplicationReadyEvent.class)
    public void checkForExpiredEnglishAuctions() {
        LocalDateTime now = LocalDateTime.now();
        List<Auction> expiredEnglishAuctions = auctionRepository.findByAuctionTypeAndActiveIsTrueAndExpirationTimeBefore(Type.ENGLISH, now);

        for (Auction auction : expiredEnglishAuctions) {
            auction.setActive(false);
            auctionRepository.save(auction);
            this.addToNotificationList(auction);
        }
    }

    public void notifyExpiredAuctionForUser(Integer auctionId, Integer userId) {
        // Retrieve Data
        User winner     = userRepository.findById(userId).get();
        Auction auction = auctionRepository.findById(auctionId).get();

        String winnerNotificationMessage = "Congratulazioni " + winner.getName() + "! Hai vinto l'asta per " + auction.getItem().getName() + ".";
        notificationService.addNotificationForUser(winner.getUserId(), winnerNotificationMessage);

        User owner = userRepository.findById(auction.getOwnerId()).get();
        String auctionerNofificationMessage = "Gentile " + owner.getName() + " " + owner.getSurname() +
                    ", la informiamo che la sua asta per l'Item " + auction.getItem().getName() + " è terminata!";
        notificationService.addNotificationForUser(owner.getUserId(), auctionerNofificationMessage);
    }

    private void addToNotificationList(Auction auction) {
        Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
        if (retrievedOffer.isPresent()) {
            Offer offer = retrievedOffer.get();
            User winner = offer.getUser();

            String notificationMessage = "Congratulazioni " + offer.getUser().getName() + "! Hai vinto l'asta per " + auction.getItem().getName() + ".";
            notificationService.addNotificationForUser(winner.getUserId(), notificationMessage);
        }
    }
}