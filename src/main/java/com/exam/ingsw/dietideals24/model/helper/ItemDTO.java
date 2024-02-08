package com.exam.ingsw.dietideals24.model.helper;

import java.io.Serializable;
import com.exam.ingsw.dietideals24.model.User;

/* [CLASS DESCRIPTION]
    - This object is sent by the client when a request to create an Item (for an Auction) occurs.
    - The real Item (the one stored inside the db) is created using this "fake" object.
**/

public class ItemDTO implements Serializable {
    private User user;
    private Integer itemId;
    private float basePrize;
    private String name;
    private String category;
    private String description;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}