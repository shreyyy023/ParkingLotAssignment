package com.example.SmartParkingLot.dto;

import java.util.List;

public class ParkingFloorDTO {
    private Long floorId;
    private int floorNumber;
    private long smallSpotCount;
    private long mediumSpotCount;
    private long largeSpotCount;
    private long XLSpotCount;
    private List<ParkingSpotDTO> parkingSpots;

    public ParkingFloorDTO() {}

    public ParkingFloorDTO(Long floorId, int floorNumber, long smallSpotCount,
                           long mediumSpotCount, long largeSpotCount, long XLSpotCount,
                           List<ParkingSpotDTO> parkingSpots) {
        this.floorId = floorId;
        this.floorNumber = floorNumber;
        this.smallSpotCount = smallSpotCount;
        this.mediumSpotCount = mediumSpotCount;
        this.largeSpotCount = largeSpotCount;
        this.XLSpotCount = XLSpotCount;
        this.parkingSpots = parkingSpots;
    }

    // Getters and Setters
    public Long getFloorId() { return floorId; }
    public void setFloorId(Long floorId) { this.floorId = floorId; }

    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int floorNumber) { this.floorNumber = floorNumber; }

    public long getSmallSpotCount() { return smallSpotCount; }
    public void setSmallSpotCount(long smallSpotCount) { this.smallSpotCount = smallSpotCount; }

    public long getMediumSpotCount() { return mediumSpotCount; }
    public void setMediumSpotCount(long mediumSpotCount) { this.mediumSpotCount = mediumSpotCount; }

    public long getLargeSpotCount() { return largeSpotCount; }
    public void setLargeSpotCount(long largeSpotCount) { this.largeSpotCount = largeSpotCount; }

    public long getXLSpotCount() { return XLSpotCount; }
    public void setXLSpotCount(long XLSpotCount) { this.XLSpotCount = XLSpotCount; }

    public List<ParkingSpotDTO> getParkingSpots() { return parkingSpots; }
    public void setParkingSpots(List<ParkingSpotDTO> parkingSpots) { this.parkingSpots = parkingSpots; }
}
