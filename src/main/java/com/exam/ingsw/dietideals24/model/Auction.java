package com.exam.ingsw.dietideals24.model;

import java.util.Set;
import java.sql.Date;
import java.sql.Time;
import jakarta.persistence.*;
import com.exam.ingsw.dietideals24.enums.Type;

@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auctionId;

    @Column(nullable = false)
    private int ownerId;

    @Column(nullable = false)
    private Type auctionType;

    @Column(nullable = false)
    private float currentOfferValue;

    @Column
    private Date expirationDate;

    @Column
    private Time expirationTime;

    @Column
    private boolean terminated;

    // 1 to 1 Relation with Item
    @OneToOne
    @JoinColumn(name =  "item_id", referencedColumnName = "itemId", nullable = false)
    private Item item;

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

    public int getOwnerId() { return ownerId; }

    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Time getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Time expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Set<Offer> getOffers() {
        return offers;
    }

    public void setOffers(Set<Offer> offers) {
        this.offers = offers;
    }
}