package com.exam.ingsw.dietideals24.model.helper;

public class AuctionStatusDTO {
    private final boolean active;
    private final String message;
    private final long timeRemaining; // Seconds

    public AuctionStatusDTO(boolean active, String message, long timeRemaining) {
        this.active        = active;
        this.message       = message;
        this.timeRemaining = timeRemaining;
    }

    public boolean isActive() {
        return active;
    }

    public String getMessage() {
        return message;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }
}