package com.exam.ingsw.dietideals24.model;

import java.util.Set;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import com.exam.ingsw.dietideals24.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private LocalDateTime expirationTime;

    @Column
    private int amountOfTimeToReset; // Specifies seconds

    @Column
    private boolean active;

    // 1 to 1 Relation with Item
    @OneToOne
    @JoinColumn(name =  "item_id", referencedColumnName = "itemId", nullable = false)
    private Item item;

    @OneToMany(mappedBy = "auction")
    @JsonIgnore
    private Set<Offer> offers;

    /* METHODS */

    public void updateStatusForSilentAuction() {
        if (Type.SILENT.equals(auctionType) && expirationDate != null) {
            if (expirationDate.before(new Date(System.currentTimeMillis()))) {
                setActive(false); // Silent Auction has expired
            }
        }
    }

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

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setAmountOfTimeToReset(int timeToReset) {
        this.amountOfTimeToReset = timeToReset;
    }

    public int getAmountOfTimeToReset() {
        return amountOfTimeToReset;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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