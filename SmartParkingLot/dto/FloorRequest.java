package com.example.SmartParkingLot.dto;

public class FloorRequest {

    private int floorNumber;
    private int totalSmallSpots;
    private int totalMediumSpots;
    private int totalLargeSpots;
    private int totalXlSpots;

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public int getTotalSmallSpots() {
        return totalSmallSpots;
    }

    public void setTotalSmallSpots(int totalSmallSpots) {
        this.totalSmallSpots = totalSmallSpots;
    }

    public int getTotalMediumSpots() {
        return totalMediumSpots;
    }

    public void setTotalMediumSpots(int totalMediumSpots) {
        this.totalMediumSpots = totalMediumSpots;
    }

    public int getTotalLargeSpots() {
        return totalLargeSpots;
    }

    public void setTotalLargeSpots(int totalLargeSpots) {
        this.totalLargeSpots = totalLargeSpots;
    }

    public int getTotalXlSpots() {
        return totalXlSpots;
    }

    public void setTotalXlSpots(int totalXlSpots) {
        this.totalXlSpots = totalXlSpots;
    }
}
