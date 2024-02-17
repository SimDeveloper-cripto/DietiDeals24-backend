package com.exam.ingsw.dietideals24.service.Interface;

import java.util.Optional;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.helper.AuctionStatusDTO;
import com.exam.ingsw.dietideals24.exception.AuctionExpiredException;
import com.exam.ingsw.dietideals24.exception.AuctionNotFoundException;

public interface IAuctionService {
    void createAuction(Auction auction);
    Optional<Auction> findAuctionByItemIdOrNameOrDescription(Integer itemId, String name, String description);
    void closeAuction(Integer auctionId);
    AuctionStatusDTO getTimeRemaining(Integer auctionId, Integer userId) throws AuctionNotFoundException, AuctionExpiredException;
    Float getWinningBid(Integer itemId);
}