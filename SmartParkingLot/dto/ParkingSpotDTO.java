package com.example.SmartParkingLot.dto;

import com.example.SmartParkingLot.entity.SpotType;

public class ParkingSpotDTO {
    private Long spotId;
    private int spotNumber;
    private SpotType spotType;
    private boolean occupied;

    public ParkingSpotDTO() {}

    public ParkingSpotDTO(Long spotId, int spotNumber, SpotType spotType, boolean occupied) {
        this.spotId = spotId;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.occupied = occupied;
    }

    // Getters and Setters
    public Long getSpotId() { return spotId; }
    public void setSpotId(Long spotId) { this.spotId = spotId; }

    public int getSpotNumber() { return spotNumber; }
    public void setSpotNumber(int spotNumber) { this.spotNumber = spotNumber; }

    public SpotType getSpotType() { return spotType; }
    public void setSpotType(SpotType spotType) { this.spotType = spotType; }

    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
