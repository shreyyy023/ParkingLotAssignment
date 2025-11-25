package com.example.SmartParkingLot.dto;

import com.example.SmartParkingLot.entity.SpotType;
import java.time.LocalDateTime;

public class ParkingSessionDTO {

    private Long sessionId;
    private String vehicleNumber;
    private int spotNumber;
    private int floorNumber;
    private SpotType spotType;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Integer totalMinutes;
    private Integer totalCost;

    public ParkingSessionDTO(Long sessionId,
                             String vehicleNumber,
                             int spotNumber,
                             int floorNumber,
                             SpotType spotType,
                             LocalDateTime entryTime,
                             LocalDateTime exitTime,
                             Integer totalMinutes,
                             Integer totalCost) {

        this.sessionId = sessionId;
        this.vehicleNumber = vehicleNumber;
        this.spotNumber = spotNumber;
        this.floorNumber = floorNumber;
        this.spotType = spotType;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.totalMinutes = totalMinutes; // use constructor parameter
        this.totalCost = totalCost;       // use constructor parameter
    }


    // GETTERS + SETTERS

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public void setSpotNumber(int spotNumber) {
        this.spotNumber = spotNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Integer getTotalMinutes() {
        return totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this.totalMinutes = totalMinutes;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }
}
