package com.example.SmartParkingLot.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "parking_sessions")
public class ParkingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private ParkingSpot parkingSpot;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Integer totalMinutes;

    private Integer totalCost;

    public ParkingSession() {}

    public ParkingSession(Vehicle vehicle, ParkingSpot spot, LocalDateTime entryTime) {
        this.vehicle = vehicle;
        setParkingSpot(spot);
        this.entryTime = entryTime;
    }

    public void endSession(LocalDateTime exitTime, int ratePerHour) {
        if (entryTime == null) {
            throw new IllegalStateException("Entry time not set. Cannot end session.");
        }

        this.exitTime = exitTime;
        this.totalMinutes = (int) Duration.between(entryTime, exitTime).toMinutes();

        // Calculate hours (always round up, min 1 hour)
        long hours = (long) Math.ceil(totalMinutes / 60.0);
        if (hours < 1) hours = 1; // minimum 1 hour charge

        this.totalCost = (int) (hours * ratePerHour);
    }




    public Long getSessionId() {
        return sessionId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;

    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public void setExitTime(LocalDateTime exitTime) { this.exitTime = exitTime; }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void calculateCost(int ratePerHour) {
        if (entryTime == null) {
            throw new IllegalStateException("Entry time not set. Cannot calculate cost.");
        }

        LocalDateTime endTime = (exitTime != null) ? exitTime : LocalDateTime.now();

        this.totalMinutes = (int) Duration.between(entryTime, endTime).toMinutes();
        long hours = (long) Math.ceil(totalMinutes / 60.0);
        this.totalCost = (int) (hours * ratePerHour);
    }

    public Integer getTotalCost() {

        return totalCost;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

}
