package com.exam.ingsw.dietideals24.service.scheduler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.helper.OfferDTO;

@Service
public class AuctionNotificationService {
    private final Map<Integer, List<String>> userSilentAuctionNotifications = new ConcurrentHashMap<>();
    private final Map<Integer, List<OfferDTO>> userSilentAuctionOffersNotifications = new ConcurrentHashMap<>();

    /* SILENT AUCTION */
    public void addSilentAuctionNotificationForUser(Integer userId, String notification) {
        userSilentAuctionNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public List<String> getSilentAuctionNotificationsForUser(Integer userId) {
        return userSilentAuctionNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public void clearSilentAuctionNotificationsForUser(Integer userId) {
        userSilentAuctionNotifications.remove(userId);
    }

    public void addSilentAuctionOfferForUser(Integer userId, OfferDTO offer) {
        userSilentAuctionOffersNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(offer);
    }

    public List<OfferDTO> getSilentAuctionOffersNotificationsForUser(Integer userId) {
        return userSilentAuctionOffersNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public void clearSilentAuctionOffersNotificationsForUser(Integer userId) {
        userSilentAuctionOffersNotifications.remove(userId);
    }

    /* ENGLISH AUCTION */
    private final Map<Integer, List<String>> userEnglishAuctionNotifications = new ConcurrentHashMap<>();

    public void addEnglishAuctionNotificationForUser(Integer userId, String notification) {
        userEnglishAuctionNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public List<String> getEnglishAuctionNotificationsForUser(Integer userId) {
        return userEnglishAuctionNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public void clearEnglishAuctionNotificationsForUser(Integer userId) {
        userEnglishAuctionNotifications.remove(userId);
    }
}