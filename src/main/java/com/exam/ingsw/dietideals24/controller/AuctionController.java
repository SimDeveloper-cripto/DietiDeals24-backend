package com.exam.ingsw.dietideals24.controller;

import java.sql.Date;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.exam.ingsw.dietideals24.enums.Type;
import com.exam.ingsw.dietideals24.model.Item;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.helper.ItemDTO;
import com.exam.ingsw.dietideals24.model.helper.AuctionDTO;
import com.exam.ingsw.dietideals24.model.helper.AuctionStatusDTO;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;
import com.exam.ingsw.dietideals24.exception.ErrorWhenParsingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.exam.ingsw.dietideals24.service.Interface.IAuctionService;
import com.exam.ingsw.dietideals24.service.scheduler.SilentAuctionSchedulerService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.exam.ingsw.dietideals24.exception.AuctionExpiredException;
import com.exam.ingsw.dietideals24.exception.AuctionNotFoundException;

@RestController
public class AuctionController {
    @Autowired
    @Qualifier("AuctionService")
    private IAuctionService auctionService;
    @Autowired
    private SilentAuctionSchedulerService auctionSchedulerService;

    @PostMapping("/auction/addAuction")
    public ResponseEntity<Void> createAuction(
            @RequestBody AuctionDTO requestedAuction) throws ErrorWhenParsingException {
        Integer itemId = requestedAuction.getRequestedItemId();
        Item item = new Item();
        item.setItemId(itemId); // Needed by JPA to insert correctly the record (have a look at the relationships)

        java.sql.Date sqlDate = null;
        LocalDateTime sqlTime = null;
        if (requestedAuction.getAuctionType().equals(Type.SILENT)) {
            try {
                String dateString = requestedAuction.getExpirationDate();
                SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate  = inputDate.parse(dateString);
                sqlDate = new java.sql.Date(parsedDate.getTime());
            } catch (ParseException e) {
                throw new ErrorWhenParsingException("SQL DATE PARSE ERROR, " + e.getMessage() + ", Cause: " + e.getCause());
            }
        } else if (requestedAuction.getAuctionType().equals(Type.ENGLISH)) {
            String timeString = requestedAuction.getExpirationTime();
            sqlTime = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        Auction auction = AuctionController.getAuction(requestedAuction, item, sqlDate, sqlTime);
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

                if (auction.getExpirationDate() != null)
                    auctionDTO.setExpirationDate(auction.getExpirationDate().toString());

                if (auction.getExpirationTime() != null)
                    auctionDTO.setExpirationTime(auction.getExpirationTime().toString());

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

    @PostMapping("/auction/endAuction")
    public ResponseEntity<Void> endAuction(
            @RequestParam Integer auctionId,
            @RequestParam Integer userId,
            @RequestParam Float winningBid) throws EmptyParametersException {
        if (auctionId == null || userId == null || winningBid == null) throw new EmptyParametersException("endAuction: at least one parameter is NULL!");
        else {
            auctionService.closeAuction(auctionId);
            auctionSchedulerService.notifyExpiredSilentAuctionForUser(auctionId, userId, winningBid);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /* ENGLISH AUCTION RELATED METHOD */
    @GetMapping("/auction/getRemainingTime")
    public ResponseEntity<?> getTimeRemaining(
            @RequestParam Integer auctionId,
            @RequestParam Integer userId) throws EmptyParametersException {
        if (auctionId == null || userId == null) throw new EmptyParametersException("getRemainingTime: at least one parameter is NULL!");
        else {
            try {
                AuctionStatusDTO auctionStatusDTO = auctionService.getTimeRemaining(auctionId, userId);
                return ResponseEntity.ok(auctionStatusDTO);
            } catch (AuctionNotFoundException e) {
                return ResponseEntity.notFound().build();
            } catch (AuctionExpiredException e) {
                return ResponseEntity.ok(new AuctionStatusDTO(false, "Auction has ended!", 0));
            }
        }
    }

    private static Auction getAuction(AuctionDTO requestedAuction, Item item, Date sqlDate, LocalDateTime sqlTime) {
        Auction auction = new Auction();
        auction.setItem(item);
        auction.setActive(requestedAuction.isActive());
        auction.setOwnerId(requestedAuction.getOwnerId());
        auction.setAuctionType(requestedAuction.getAuctionType());
        auction.setCurrentOfferValue(requestedAuction.getCurrentOfferValue());
        auction.setExpirationDate(sqlDate);                                        // Note: for EnglishAuction, Date is NULL
        auction.setExpirationTime(sqlTime);                                        // Note: for SilentAuction,  Time is NULL
        auction.setAmountOfTimeToReset(requestedAuction.getAmountOfTimeToReset()); // Note: for SilentAuction, value if NULL
        return auction;
    }
}