package com.exam.ingsw.dietideals24.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "auctionId", nullable = false)
    private Auction auction;

    @Column(nullable = false)
    private float offer;

    @Column(nullable = false)
    private LocalDateTime offerDateAndTime;

    /* CONSTRUCTOR */

    public Offer() {}

    /* GETTERS AND SETTERS */

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public float getOffer() {
        return offer;
    }

    public void setOffer(float offer) {
        this.offer = offer;
    }

    public LocalDateTime getOfferDateAndTime() {
        return offerDateAndTime;
    }

    public void setOfferDateAndTime(LocalDateTime offerDateAndTime) {
        this.offerDateAndTime = offerDateAndTime;
    }

    /* METHODS */
}