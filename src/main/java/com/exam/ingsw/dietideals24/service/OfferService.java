package com.exam.ingsw.dietideals24.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.exam.ingsw.dietideals24.enums.Type;
import org.springframework.stereotype.Service;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.model.dto.OfferDTO;
import com.exam.ingsw.dietideals24.utility.MySQLDateAndTimeParser;

import org.springframework.beans.factory.annotation.Autowired;
import com.exam.ingsw.dietideals24.repository.IOfferRepository;
import com.exam.ingsw.dietideals24.repository.IAuctionRepository;
import com.exam.ingsw.dietideals24.service.serviceinterface.IOfferService;

@Service("OfferService")
public class OfferService implements IOfferService {
    @Autowired
    private IOfferRepository offerRepository;
    @Autowired
    private IAuctionRepository auctionRepository;

    @Override
    public OfferDTO getTopOfferByItemIdAndAuctionId(Integer itemId, Integer auctionId) {
        List<Offer> offers = offerRepository.findTopOfferByItemIdAndAuctionId(itemId, auctionId);
        Offer offer = offers.isEmpty() ? null : offers.getFirst();

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

        Auction auction = null;
        Optional<Auction> retrievedAuction = auctionRepository.findById(offerDTO.getAuctionId());
        if (retrievedAuction.isPresent()) auction = retrievedAuction.get();

        if (offerDTO.getAuctionType().equals(Type.ENGLISH)) {
            assert auction != null;

            // Update "expirationTime" attribute of the Auction record
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime newExpirationTime = now.plusHours(auction.getAmountOfTimeToReset());

            String newFormattedDateTime = newExpirationTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            auction.setExpirationTime(newExpirationTime);
            auctionRepository.save(auction);
        }

        offer.setUser(offerDTO.getUser());
        offer.setAuction(auction);
        offer.setOffer(offerDTO.getOffer());
        offer.setOfferDate(MySQLDateAndTimeParser.parseDate(offerDTO.getOfferDate()));
        offer.setOfferTime(MySQLDateAndTimeParser.parseTime(offerDTO.getOfferTime()));
        offerRepository.save(offer);
    }

    @Override
    public List<OfferDTO> getOffers(int itemId, int auctionId) {
        List<Offer> offers = offerRepository.findOffers(itemId, auctionId);

        if (offers.isEmpty()) return null;

        List<OfferDTO> offerDTOList = new ArrayList<>();
        for (Offer o : offers) {
            OfferDTO offerDTO = new OfferDTO();
            offerDTO.setOfferId(o.getOfferId());
            offerDTO.setAuctionId(o.getAuction().getAuctionId());
            offerDTO.setUser(o.getUser());
            offerDTO.setOffer(o.getOffer());
            offerDTO.setOfferDate(MySQLDateAndTimeParser.parseDateToString(o.getOfferDate()));
            offerDTO.setOfferTime(MySQLDateAndTimeParser.parseTimeToString(o.getOfferTime()));

            offerDTOList.add(offerDTO);
        }
        return offerDTOList;
    }
}