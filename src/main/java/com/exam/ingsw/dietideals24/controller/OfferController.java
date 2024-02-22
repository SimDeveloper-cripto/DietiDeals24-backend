package com.exam.ingsw.dietideals24.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.exam.ingsw.dietideals24.model.dto.OfferDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.service.serviceinterface.IOfferService;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

@RestController
public class OfferController {
    @Autowired
    @Qualifier("OfferService")
    private IOfferService offerService;

    /* [DESCRIPTION] Needed for English Auctions */
    @GetMapping("/offer/getBestOffer")
    public ResponseEntity<OfferDTO> getBestOffer(
            @RequestParam Integer itemId,
            @RequestParam Integer auctionId) throws EmptyParametersException {
        if (itemId == null || auctionId == null)
            throw new EmptyParametersException("Error: AuctionController --> getBestOffer() --> itemId or auctionId is NULL!");
        else {
            OfferDTO bestOffer = offerService.getTopOfferByItemIdAndAuctionId(itemId, auctionId);

            if (bestOffer != null)
                return ResponseEntity.ok(bestOffer);
            else
                return ResponseEntity.notFound().build();
        }
    }

    /* [DESCRIPTION] Needed for Silent Auctions */
    @GetMapping("/offer/getOffers")
    public ResponseEntity<List<OfferDTO>> getOffers(
            @RequestParam Integer itemId,
            @RequestParam Integer auctionId) throws EmptyParametersException {
        if (itemId == null || auctionId == null)
            throw new EmptyParametersException("Error: AuctionController --> getOffers() --> itemId or auctionId is NULL!");
        else {
            List<OfferDTO> offers = offerService.getOffers(itemId, auctionId);

            if (offers == null || offers.isEmpty())
                return ResponseEntity.notFound().build();
            else
                return ResponseEntity.ok(offers);
        }
    }

    @PostMapping("/offer/createOffer")
    public void createOffer(@RequestBody OfferDTO offerDTO) throws EmptyParametersException {
        if (offerDTO == null) throw new EmptyParametersException("Received a null reference for OfferDTO!");
        else {
            offerService.createOffer(offerDTO);
        }
    }
}