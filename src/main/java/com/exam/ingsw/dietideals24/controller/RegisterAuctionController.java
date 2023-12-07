package com.exam.ingsw.dietideals24.controller;

import org.springframework.http.ResponseEntity;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.service.IAuctionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterAuctionController {
    @Autowired
    @Qualifier("AuctionService")
    private IAuctionService auctionService;

    @PostMapping("/addAuction")
    public ResponseEntity<Auction> registerAuction(@RequestBody Auction auction) {
        return auctionService.registerAuction(auction);
    }
}