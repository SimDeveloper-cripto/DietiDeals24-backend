package com.exam.ingsw.dietideals24.JUnit;

import java.util.List;
import java.util.Arrays;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import com.exam.ingsw.dietideals24.model.Offer;
import com.exam.ingsw.dietideals24.model.Auction;
import com.exam.ingsw.dietideals24.service.OfferService;
import com.exam.ingsw.dietideals24.model.helper.OfferDTO;
import com.exam.ingsw.dietideals24.repository.IOfferRepository;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {
    @InjectMocks
    private OfferService offerService;
    @Mock
    private IOfferRepository offerRepository;

    @Test
    public void testCaseCE1_CE3() {
        int itemId    = 2;
        int auctionId = 2;

        Auction auction = new Auction();
        auction.setAuctionId(auctionId);

        Offer offer1 = new Offer();
        offer1.setOfferId((long) 2);
        offer1.setAuction(auction);

        Offer offer2 = new Offer();
        offer2.setOfferId((long) 3);
        offer2.setAuction(auction);

        Offer offer3 = new Offer();
        offer3.setOfferId((long) 4);
        offer3.setAuction(auction);

        when(offerRepository.findOffers(itemId, auctionId)).thenReturn(Arrays.asList(offer1, offer2, offer3));
        List<OfferDTO> offerDTOS = offerService.getOffers(itemId, auctionId);
        assertEquals(3, offerDTOS.size());
    }

    @Test
    public void testCaseCE2_CE3() {
        int itemId    = 0;
        int auctionId = 1;

        List<OfferDTO> offerDTOS = offerService.getOffers(itemId, auctionId);
        assertNull(offerDTOS);
    }

    @Test
    public void testCaseCE1_CE4() {
        int itemId    = 1;
        int auctionId = -3;

        List<OfferDTO> offerDTOS = offerService.getOffers(itemId, auctionId);
        assertNull(offerDTOS);
    }
}