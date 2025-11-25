package com.example.SmartParkingLot.entity;

public enum VehicleType {
    BIKE(SpotType.SMALL),
    CAR(SpotType.MEDIUM),
    TRUCK(SpotType.LARGE),
    BUS(SpotType.XL);

    private final SpotType requiredSpotType;

    VehicleType(SpotType requiredSpotType) {
        this.requiredSpotType = requiredSpotType;
    }

    public SpotType getRequiredSpotType() {
        return requiredSpotType;
    }
}
