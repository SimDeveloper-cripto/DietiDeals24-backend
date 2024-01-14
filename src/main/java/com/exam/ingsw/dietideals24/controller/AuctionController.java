package com.exam.ingsw.dietideals24.controller;

import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.helper.AuctionDTO;
import com.exam.ingsw.dietideals24.exception.ErrorWhenParsingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exam.ingsw.dietideals24.service.IAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class AuctionController {
    @Autowired
    @Qualifier("AuctionService")
    private IAuctionService auctionService;

    @PostMapping("/auction/addSilentAuction")
    public ResponseEntity<Void> createAuction(
            @RequestBody AuctionDTO requestedAuction) throws ErrorWhenParsingException {
        Integer itemId = requestedAuction.getRequestedItemId();
        Item item = new Item();
        item.setItemId(itemId); // Needed by JPA to insert correctly the record (have a look at the relationships)

        java.sql.Date sqlDate;
        try {
            String dateString = requestedAuction.getExpirationDate();
            SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate  = inputDate.parse(dateString);
            sqlDate = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            throw new ErrorWhenParsingException("SQL DATE PARSE ERROR, " + e.getMessage() + ", Cause: " + e.getCause());
        }

        Auction auction = new Auction();
        auction.setItem(item);
        auction.setExpirationDate(sqlDate);
        auction.setActive(requestedAuction.isActive());
        auction.setOwnerId(requestedAuction.getOwnerId());
        auction.setAuctionType(requestedAuction.getAuctionType());
        auction.setExpirationTime(requestedAuction.getExpirationTime()); // Note: for SilentAuction Time is NULL
        auction.setCurrentOfferValue(requestedAuction.getCurrentOfferValue());

        auctionService.createAuction(auction);
        return ResponseEntity.ok().build();
    }
}