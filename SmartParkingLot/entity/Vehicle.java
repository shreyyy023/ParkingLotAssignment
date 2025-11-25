package com.example.SmartParkingLot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    // REQUIRED SpotType — added for allocation logic
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotType requiredSpotType;

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public SpotType getRequiredSpotType() {
        return requiredSpotType;
    }

    public void setRequiredSpotType(SpotType requiredSpotType) {
        this.requiredSpotType = requiredSpotType;
    }
}
