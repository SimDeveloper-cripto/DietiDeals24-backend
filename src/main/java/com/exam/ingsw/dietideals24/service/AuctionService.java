package com.exam.ingsw.dietideals24.service;

import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;

@Service("AuctionService")
public class AuctionService implements IAuctionService {
    @Autowired
    private IAuctionRepository auctionRepository;

    @Override
    public void createAuction(Auction auction) {
        auctionRepository.save(auction);
    }
}