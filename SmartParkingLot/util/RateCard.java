package com.example.SmartParkingLot.util;

import com.example.SmartParkingLot.entity.VehicleType;

public class RateCard {

    public static int getRate(VehicleType type) {
        return switch (type) {
            case CAR -> 50;
            case BIKE -> 20;
            case TRUCK -> 100;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
