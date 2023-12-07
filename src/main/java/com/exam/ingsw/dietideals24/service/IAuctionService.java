package com.exam.ingsw.dietideals24.service;

import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.http.ResponseEntity;

public interface IAuctionService {
    ResponseEntity<Auction> registerAuction(Auction auction);
}