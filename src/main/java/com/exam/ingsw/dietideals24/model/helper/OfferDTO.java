package com.exam.ingsw.dietideals24.model.helper;

import com.exam.ingsw.dietideals24.model.User;
// import com.exam.ingsw.dietideals24.model.Auction;

/* [CLASS DESCRIPTION]
    - This object is sent by the client when a request to get/create an Offer occurs.
    - The real Offer (the one stored inside the db) is created using this "fake" object.
**/

public class OfferDTO {
    private Long offerId;
    private Integer auctionId;
    private User user;

    // private Auction auction; // We don't need the auction

    private float offer;
    private String offerDate;
    private String offerTime;

    /* GETTERS AND SETTERS */
    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getOffer() {
        return offer;
    }

    public void setOffer(float offer) {
        this.offer = offer;
    }

    public String getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(String offerDate) {
        this.offerDate = offerDate;
    }

    public String getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(String offerTime) {
        this.offerTime = offerTime;
    }
}