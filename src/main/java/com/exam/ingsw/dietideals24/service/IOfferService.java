package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.helper.OfferDTO;

public interface IOfferService {
    OfferDTO getTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId);
}