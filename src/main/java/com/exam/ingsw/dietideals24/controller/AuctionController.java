package com.exam.ingsw.dietideals24.controller;

import java.sql.Date;
import java.util.Optional;

import com.exam.ingsw.dietideals24.enums.Type;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;
import com.exam.ingsw.dietideals24.model.helper.AuctionDTO;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;
import com.exam.ingsw.dietideals24.exception.ErrorWhenParsingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.exam.ingsw.dietideals24.service.Interface.IAuctionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class AuctionController {
    @Autowired
    @Qualifier("AuctionService")
    private IAuctionService auctionService;

    @PostMapping("/auction/addAuction")
    public ResponseEntity<Void> createAuction(
            @RequestBody AuctionDTO requestedAuction) throws ErrorWhenParsingException {
        Integer itemId = requestedAuction.getRequestedItemId();
        Item item = new Item();
        item.setItemId(itemId); // Needed by JPA to insert correctly the record (have a look at the relationships)

        java.sql.Date sqlDate = null;
        if (requestedAuction.getAuctionType().equals(Type.SILENT)) {
            try {
                String dateString = requestedAuction.getExpirationDate();
                SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate  = inputDate.parse(dateString);
                sqlDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                throw new ErrorWhenParsingException("SQL DATE PARSE ERROR, " + e.getMessage() + ", Cause: " + e.getCause());
            }
        }

        Auction auction = AuctionController.getAuction(requestedAuction, item, sqlDate);
        auctionService.createAuction(auction);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/auction/findAuction")
    public ResponseEntity<AuctionDTO> findAuction(
            @RequestParam Integer itemId,
            @RequestParam String name,
            @RequestParam String description) throws EmptyParametersException  {
        if (itemId == null || name == null || description == null) throw new EmptyParametersException("findAuction: at least one parameter is NULL!");
        else {
            Optional<Auction> retrievedAuction = auctionService.findAuctionByItemIdOrNameOrDescription(itemId, name, description);

            if (retrievedAuction.isPresent()) {
                Auction auction = retrievedAuction.get();
                Item item = auction.getItem();

                AuctionDTO auctionDTO = new AuctionDTO();
                auctionDTO.setAuctionId(auction.getAuctionId());
                auctionDTO.setOwnerId(auction.getOwnerId());
                auctionDTO.setActive(auction.isActive());
                auctionDTO.setAuctionType(auction.getAuctionType());
                auctionDTO.setCurrentOfferValue(auction.getCurrentOfferValue());
                auctionDTO.setExpirationDate(auction.getExpirationDate().toString());
                auctionDTO.setExpirationTime(auction.getExpirationTime());

                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setItemId(item.getItemId());
                itemDTO.setBasePrize(item.getBasePrize());
                itemDTO.setName(name);
                itemDTO.setDescription(description);
                itemDTO.setCategory(item.getCategory());
                itemDTO.setUser(item.getUser());

                auctionDTO.setRequestedItemDTO(itemDTO);
                return ResponseEntity.ok(auctionDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    private static Auction getAuction(AuctionDTO requestedAuction, Item item, Date sqlDate) {
        Auction auction = new Auction();
        auction.setItem(item);
        auction.setActive(requestedAuction.isActive());
        auction.setOwnerId(requestedAuction.getOwnerId());
        auction.setAuctionType(requestedAuction.getAuctionType());
        auction.setCurrentOfferValue(requestedAuction.getCurrentOfferValue());
        auction.setExpirationDate(sqlDate);                              // Note: for EnglishAuction, Date is NULL
        auction.setExpirationTime(requestedAuction.getExpirationTime()); // Note: for SilentAuction,  Time is NULL
        return auction;
    }
}