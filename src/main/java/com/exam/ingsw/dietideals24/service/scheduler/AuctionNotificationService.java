package com.exam.ingsw.dietideals24.service.scheduler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AuctionNotificationService {
    private final Map<Integer, List<String>> userNotifications = new ConcurrentHashMap<>();

    public void addNotificationForUser(Integer userId, String notification) {
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public List<String> getNotificationsForUser(Integer userId) {
        return userNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public void clearNotificationsForUser(Integer userId) {
        userNotifications.remove(userId);
    }
}