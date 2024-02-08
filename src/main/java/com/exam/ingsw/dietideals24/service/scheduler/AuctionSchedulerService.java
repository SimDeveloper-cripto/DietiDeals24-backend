package com.exam.ingsw.dietideals24.service.scheduler;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import jakarta.annotation.PostConstruct;
import com.exam.ingsw.dietideals24.model.User;
import com.exam.ingsw.dietideals24.model.Offer;
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

// TODO: CREARE UN MECCANISMO SIMILE PER LE ASTE ALL'INGLESE (VINCITORE E PERDENTI DEVONO RICEVERE LA NOTIFICA)

@Service
public class AuctionSchedulerService {
    @Autowired
    private IAuctionRepository auctionRepository;
    @Autowired
    private IOfferRepository offerRepository;
    private final List<String> pendingNotifications = new ArrayList<>();
    @Autowired
    private SilentAuctionNotificationService notificationService;

    @PostConstruct
    public void checkForExpiredSilentAuctionsOnStartUp() {
        checkForExpiredSilentAuctions();
    }

    @Scheduled(cron = "0 0 0 * * ?") // Execute every day at midnight
    public void checkForExpiredSilentAuctions() {
        List<Auction> expiredSilentAuctions = auctionRepository.findByAuctionTypeAndActiveIsTrueAndExpirationDateBefore(
                Type.SILENT, new Date(System.currentTimeMillis()));

        for (Auction auction : expiredSilentAuctions) {
            auction.updateStatusForSilentAuction();
            auctionRepository.save(auction);

            Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
            if (retrievedOffer.isPresent()) {
                Offer offer = retrievedOffer.get();
                User winner = offer.getUser();

                String notificationMessage = "Congratulazioni! Hai vinto l'asta per " + auction.getItem().getName() + ".";
                notificationService.addNotificationForUser(winner.getUserId(), notificationMessage);
            }
        }
    }
}