package com.exam.ingsw.dietideals24.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private float basePrize;

    @Lob
    private byte[] image; // The item's image corresponds to a BLOB datatype

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false) // Fk that points to the User's userId
    private User user;

    // TODO: Here I set nullable = false because I think the Item will be uploaded to the app only in auctions
    @ManyToOne
    @JoinColumn(name = "auctionId", nullable = false) // Fk that points to the Auction's auctionId
    private Auction auction;

    /* CONSTRUCTOR */

    public Item() {}

    /* GETTERS AND SETTERS */

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getBasePrize() {
        return basePrize;
    }

    public void setBasePrize(float basePrize) {
        this.basePrize = basePrize;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    /* METHODS */
}