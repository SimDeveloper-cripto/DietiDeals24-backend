package com.exam.ingsw.dietideals24.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.service.scheduler.AuctionSchedulerService;

@RestController
public class AuctionNotificationController {
    private final AuctionSchedulerService auctionSchedulerService;

    @Autowired
    public AuctionNotificationController(AuctionSchedulerService auctionSchedulerService) {
        this.auctionSchedulerService = auctionSchedulerService;
    }

    @GetMapping("/auction/notifications/pending")
    public ResponseEntity<List<String>> sendPendingNotifications() {
        List<String> notifications = auctionSchedulerService.getPendingNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}