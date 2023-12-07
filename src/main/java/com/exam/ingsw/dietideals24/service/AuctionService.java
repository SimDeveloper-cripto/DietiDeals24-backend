package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;

@Service("AuctionService")
public class AuctionService implements IAuctionService {
    @Autowired
    private IAuctionRepository auctionRepository;

    public AuctionService() {}

    @Override
    public ResponseEntity<Auction> registerAuction(Auction auction) {
        try {
            Auction savedAuction = auctionRepository.save(auction);
            return new ResponseEntity<>(savedAuction, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}