package com.exam.ingsw.dietideals24.service.scheduler;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.exam.ingsw.dietideals24.model.helper.OfferDTO;
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
            User owner = userRepository.findById(auction.getOwnerId()).get();
            Integer ownerId = owner.getUserId();

            auction.updateStatusForSilentAuction();
            auctionRepository.save(auction);

            // Checking the TOP record, we assure that at least there is one offer for that auctioned Item
            Optional<Offer> retrievedOffer = offerRepository.findTopByAuctionAuctionIdOrderByOfferDateDescOfferTimeDesc(auction.getAuctionId());
            if (retrievedOffer.isPresent()) {
                List<Offer> offers = offerRepository.findOffers(auction.getItem().getItemId(), auction.getAuctionId());

                // Notification for Auction owner
                for (Offer o : offers) {
                    OfferDTO offerDTO = new OfferDTO();
                    offerDTO.setOfferId(o.getOfferId());
                    offerDTO.setUser(o.getUser());
                    offerDTO.setOffer(o.getOffer());
                    offerDTO.setAuctionType(o.getAuction().getAuctionType());

                    // Date and Time are not relevant to set in this case
                    notificationService.addSilentAuctionOfferForUser(ownerId, offerDTO);
                }

                // Notifications for each User
                for (Offer o : offers) {
                    User user = userRepository.findById(o.getUser().getUserId()).get();
                    String message = "Gentile " + user.getName() + " " + user.getSurname() +
                            ", la informiamo che la sua asta per l'Item " + auction.getItem().getName() + " è terminata!";
                    notificationService.addSilentAuctionNotificationForUser(user.getUserId(), message);
                }
            } else {
                String auctionerNofificationMessage = "Gentile " + owner.getName() + " " + owner.getSurname() +
                        ", la informiamo che la sua asta per l'Item " + auction.getItem().getName() + " è terminata senza offerte!";
                notificationService.addSilentAuctionNotificationForUser(ownerId, auctionerNofificationMessage);
            }
        }
    }

    public void notifyExpiredSilentAuctionForUser(Integer auctionId, Integer userId) {
        User winner     = userRepository.findById(userId).get();
        Auction auction = auctionRepository.findById(auctionId).get();

        // Save winner information
        auction.setWinnerId(winner.getUserId());
        auctionRepository.save(auction);

        String winnerNotificationMessage = "Congratulazioni " + winner.getName() + "! Hai vinto l'asta per " + auction.getItem().getName() + ".";
        notificationService.addSilentAuctionNotificationForUser(winner.getUserId(), winnerNotificationMessage);

        User owner = userRepository.findById(auction.getOwnerId()).get();
        String auctionerNofificationMessage = "Gentile " + owner.getName() + " " + owner.getSurname() +
                    ", la informiamo che la sua asta per l'Item " + auction.getItem().getName() + " è terminata con successo!";
        notificationService.addSilentAuctionNotificationForUser(owner.getUserId(), auctionerNofificationMessage);
    }
}