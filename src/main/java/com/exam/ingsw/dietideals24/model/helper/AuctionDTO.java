package com.exam.ingsw.dietideals24.model.helper;

import java.sql.Time;
import java.io.Serializable;
import com.exam.ingsw.dietideals24.enums.Type;

/* [CLASS DESCRIPTION]
    - This object is sent by the client when a request to create an Auction occurs.
    - The real Auction (the one stored inside the db) is created using this "fake" object.
**/

public class AuctionDTO implements Serializable {
    private int ownerId;
    private boolean active;
    private Type auctionType;
    private Time expirationTime;
    private String expirationDate;
    private float currentOfferValue;
    private Integer auctionId;
    private ItemDTO requestedItem;
    private Integer requestedItemId;

    /* GETTERS AND SETTERS */

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getRequestedItemId() {
        return requestedItemId;
    }

    public void setRequestedItemId(Integer requestedItemId) {
        this.requestedItemId = requestedItemId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Time getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Time expirationTime) {
        this.expirationTime = expirationTime;
    }

    public ItemDTO getRequestedItemDTO() {
        return requestedItem;
    }

    public void setRequestedItemDTO(ItemDTO requestedItem) {
        this.requestedItem = requestedItem;
    }
}