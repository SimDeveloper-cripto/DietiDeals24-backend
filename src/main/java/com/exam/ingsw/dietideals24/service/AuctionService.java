package com.exam.ingsw.dietideals24.service;

import java.time.Duration;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.exception.AuctionExpiredException;
import com.exam.ingsw.dietideals24.exception.AuctionNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;
import com.exam.ingsw.dietideals24.model.helper.AuctionStatusDTO;
import com.exam.ingsw.dietideals24.service.Interface.IAuctionService;

@Service("AuctionService")
public class AuctionService implements IAuctionService {
    @Autowired
    private IAuctionRepository auctionRepository;

    @Override
    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    @Override
    public Optional<Auction> findAuctionByItemIdOrNameOrDescription(Integer itemId, String name, String description) {
        return auctionRepository.findByItemIdOrItemNameOrItemDescription(itemId, name, description);
    }

    @Override
    public void closeAuction(Integer auctionId) {
        auctionRepository.closeAuction(auctionId);
    }

    /* ENGLISH AUCTION RELATED METHOD */
    @Override
    public AuctionStatusDTO getTimeRemaining(Integer auctionId, Integer userId) throws AuctionNotFoundException, AuctionExpiredException {
        Optional<Auction> retrievedAuction = auctionRepository.findById(auctionId);
        if (retrievedAuction.isEmpty()) {
            throw new AuctionNotFoundException("Could not find Auction with ID: " + auctionId);
        }

        Auction auction       = retrievedAuction.get();
        LocalDateTime now     = LocalDateTime.now();
        LocalDateTime expTime = auction.getExpirationTime();

        if (now.isAfter(expTime)) {
            auction.setActive(false);
            auctionRepository.save(auction);
            throw new AuctionExpiredException("Auction with ID: " + auctionId + " has ended!");
        } else {
            Duration duration = Duration.between(now, expTime);
            long secondsRemaining = duration.toSeconds();
            return new AuctionStatusDTO(true,"Auction is still active!", secondsRemaining);
        }
    }

    @Override
    public Float getWinningBid(Integer itemId) {
        return auctionRepository.findWinningBidByItemId(itemId);
    }
}