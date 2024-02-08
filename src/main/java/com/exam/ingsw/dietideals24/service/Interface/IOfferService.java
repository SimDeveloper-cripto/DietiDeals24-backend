package com.exam.ingsw.dietideals24.service.Interface;

import java.util.List;
import com.exam.ingsw.dietideals24.model.helper.OfferDTO;

public interface IOfferService {
    /* Needed for English Auctions */
    OfferDTO getTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId);

    void createOffer(OfferDTO offerDTO);

    /* Needed for Silent Auctions */
    List<OfferDTO> getOffers(Integer itemId, Integer auctionId);
}