package com.exam.ingsw.dietideals24.service;

import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.helper.OfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.utility.MySQLDateAndTimeParser;

import com.exam.ingsw.dietideals24.repository.IOfferRepository;
import com.exam.ingsw.dietideals24.service.Interface.IOfferService;

@Service("OfferService")
public class OfferService implements IOfferService {
    @Autowired
    private IOfferRepository offerRepository;

    @Override
    public OfferDTO getTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId) {
        Offer offer = offerRepository.findTopOfferByItemIdAndAuctionId(itemId, auctionId);

        if (offer != null) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(offer.getOfferId());
            offerDTO.setUser(offer.getUser());
            offerDTO.setOffer(offer.getOffer());
            offerDTO.setOfferDate(offer.getOfferDate().toString());
            offerDTO.setOfferTime(offer.getOfferTime().toString());
            return offerDTO;
        }

        return null;
    }

    @Override
    public void createOffer(OfferDTO offerDTO) {
        Offer offer = new Offer();

        Auction auction = new Auction();
        auction.setAuctionId(offerDTO.getAuctionId());

        offer.setUser(offerDTO.getUser());
        offer.setUser(offerDTO.getUser());
        offer.setAuction(auction);
        offer.setOffer(offerDTO.getOffer());
        offer.setOfferDate(MySQLDateAndTimeParser.parseDate(offerDTO.getOfferDate()));
        offer.setOfferTime(MySQLDateAndTimeParser.parseTime(offerDTO.getOfferTime()));

        offerRepository.save(offer);
    }
}