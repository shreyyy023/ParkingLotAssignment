package com.example.SmartParkingLot.dto;

import com.example.SmartParkingLot.entity.SpotType;

public class ParkingSpotResponse {
    private Long id;
    private int spotNumber;       // if you have this field; if not, ignore/replace as needed
    private SpotType spotType;
    private boolean occupied;

    public ParkingSpotResponse() {}

    public ParkingSpotResponse(Long id, int spotNumber, SpotType spotType, boolean occupied) {
        this.id = id;
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        this.occupied = occupied;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getSpotNumber() { return spotNumber; }
    public void setSpotNumber(int spotNumber) { this.spotNumber = spotNumber; }

    public SpotType getSpotType() { return spotType; }
    public void setSpotType(SpotType spotType) { this.spotType = spotType; }

    public boolean isOccupied() { return occupied; }
    public void setOccupied(boolean occupied) { this.occupied = occupied; }
}
