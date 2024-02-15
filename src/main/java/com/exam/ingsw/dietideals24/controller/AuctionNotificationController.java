package com.exam.ingsw.dietideals24.controller;

import java.util.List;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.ingsw.dietideals24.model.helper.OfferDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.service.scheduler.AuctionNotificationService;

@RestController
public class AuctionNotificationController {
    @Autowired
    private AuctionNotificationService notificationService;

    @GetMapping("/auction/silent/notificationsForUser/pending")
    public ResponseEntity<List<String>> sendPendingNotificationsForUser(@RequestParam Integer userId) {
        List<String> notifications = notificationService.getSilentAuctionNotificationsForUser(userId);
        notificationService.clearSilentAuctionNotificationsForUser(userId);

        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
    }

    @GetMapping("/auction/silent/getOfferList/pending")
    public ResponseEntity<List<OfferDTO>> sendPendingOffersSilentAuction(@RequestParam Integer itemId) {
        List<OfferDTO> offerDTOS = notificationService.getOfferMap(itemId);
        notificationService.clearOfferMap(itemId);

        if (offerDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(offerDTOS, HttpStatus.OK);
        }
    }

    @GetMapping("/auction/english/notificationsForUser/pending")
    public ResponseEntity<List<String>> sendEnglishAuctionPendingNotificationsForUser(@RequestParam Integer userId) {
        List<String> notifications = notificationService.getEnglishAuctionNotificationsForUser(userId);
        notificationService.clearEnglishAuctionNotificationsForUser(userId);

        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        }
    }
}