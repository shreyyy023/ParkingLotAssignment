package com.example.SmartParkingLot.dto;

import com.example.SmartParkingLot.entity.SpotType;

public class ParkingSpotRequest {
    private SpotType spotType;
    private Boolean occupied; // optional for create (default false)

    public ParkingSpotRequest() {}

    public ParkingSpotRequest(SpotType spotType, Boolean occupied) {
        this.spotType = spotType;
        this.occupied = occupied;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }
}
