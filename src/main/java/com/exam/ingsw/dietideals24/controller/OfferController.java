package com.exam.ingsw.dietideals24.controller;

import org.springframework.http.ResponseEntity;
import com.exam.ingsw.dietideals24.model.helper.OfferDTO;
import com.exam.ingsw.dietideals24.service.Interface.IOfferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.exam.ingsw.dietideals24.exception.EmptyParametersException;

@RestController
public class OfferController {
    @Autowired
    @Qualifier("OfferService")
    private IOfferService offerService;

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
}