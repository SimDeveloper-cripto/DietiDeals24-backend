package com.exam.ingsw.dietideals24.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Auction;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;
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
}