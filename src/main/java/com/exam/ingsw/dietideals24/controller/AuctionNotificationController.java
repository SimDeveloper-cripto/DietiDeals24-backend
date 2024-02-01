package com.exam.ingsw.dietideals24.controller;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.service.NotificationService;

@RestController
public class AuctionNotificationController {
    @Autowired
    private NotificationService notificationService;

    // TODO: FOR NOW IT ONLY WORKS FOR SILENT AUCTIONS
    @GetMapping("/auction/silent/notificationsForUser/pending")
    public ResponseEntity<List<String>> sendPendingNotificationsForUser(@RequestParam Integer userId) {
        List<String> notifications = notificationService.getNotificationsForUser(userId);

        if (notifications.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}