package com.exam.ingsw.dietideals24.model;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;

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
    private LocalDate offerDate;

    @Column(nullable = false)
    private LocalTime offerTime;

    /* CONSTRUCTOR */

    public Offer() {
        this.offerDate = LocalDate.now();
        this.offerTime = LocalTime.now();
    }

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

    public LocalDate getOfferDate() {
        return offerDate;
    }

    public void setOfferDate(LocalDate offerDate) {
        this.offerDate = offerDate;
    }

    public LocalTime getOfferTime() {
        return offerTime;
    }

    public void setOfferTime(LocalTime offerTime) {
        this.offerTime = offerTime;
    }
}