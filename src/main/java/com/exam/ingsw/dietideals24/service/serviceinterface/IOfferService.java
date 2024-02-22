package com.exam.ingsw.dietideals24.service.serviceinterface;

import java.util.List;
import com.exam.ingsw.dietideals24.model.dto.OfferDTO;

public interface IOfferService {
    /* Needed for English Auctions */
    OfferDTO getTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId);

    void createOffer(OfferDTO offerDTO);

    /* Needed for Silent Auctions */
    List<OfferDTO> getOffers(int itemId, int auctionId);
}