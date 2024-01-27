package com.exam.ingsw.dietideals24.service.Interface;

import java.util.Optional;
import com.exam.ingsw.dietideals24.model.Auction;

public interface IAuctionService {
    void createAuction(Auction auction);
    Optional<Auction> findAuctionByItemIdOrNameOrDescription(Integer itemId, String name, String description);
}