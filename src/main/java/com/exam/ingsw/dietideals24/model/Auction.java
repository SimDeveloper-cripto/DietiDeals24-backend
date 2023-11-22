package com.exam.ingsw.dietideals24.model;

import java.util.Set;
import java.time.Duration;
import jakarta.persistence.*;
import com.exam.ingsw.dietideals24.enums.Type;

@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auctionId;

    @Column(nullable = false)
    private Type auctionType;

    @Column(nullable = false)
    private float currentOfferValue;

    @Column(nullable = false)
    private Duration duration; // Example: auction.setDurationInSeconds(604800); --> 604800 means 7 days of duration

    @OneToMany(mappedBy =  "auction", fetch = FetchType.EAGER)
    private Set<Item> items;

    @OneToMany(mappedBy = "auction")
    private Set<Offer> offers;

    /* CONSTRUCTOR */

    public Auction() {}

    /* GETTERS AND SETTERS */

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Type getAuctionType() {
        return auctionType;
    }

    public void setAuctionType(Type auctionType) {
        this.auctionType = auctionType;
    }

    public float getCurrentOfferValue() {
        return currentOfferValue;
    }

    public void setCurrentOfferValue(float currentOfferValue) {
        this.currentOfferValue = currentOfferValue;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }

    /* METHODS */
}