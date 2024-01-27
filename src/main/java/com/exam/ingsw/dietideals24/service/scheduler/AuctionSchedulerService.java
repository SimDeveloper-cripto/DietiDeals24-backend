package com.exam.ingsw.dietideals24.service.scheduler;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import jakarta.annotation.PostConstruct;
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
public class AuctionSchedulerService {
    private final IAuctionRepository auctionRepository;
    private final List<String> pendingNotifications = new ArrayList<>();

    @Autowired
    public AuctionSchedulerService(IAuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

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
            pendingNotifications.add("Auction expired: " + auction.getAuctionId()); // TODO: TROVARE QUALCOSA DI MEGLIO DA INSERIRE
        }
    }

    public List<String> getPendingNotifications() {
        List<String> notifications = new ArrayList<>(pendingNotifications);
        pendingNotifications.clear();
        return notifications;
    }
}