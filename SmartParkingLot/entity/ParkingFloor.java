package com.example.SmartParkingLot.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_floors")
public class ParkingFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId;

    @Column(nullable = false)
    private int floorNumber;

    @OneToMany(
            mappedBy = "floor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

    @Column
    private int totalSmallSpots;

    @Column
    private int totalMediumSpots;

    @Column
    private int totalLargeSpots;

    @Column
    private int totalXlSpots;


    public ParkingFloor() {}

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Long getFloorId() {
        return floorId;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void addSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
        spot.setFloor(this);

        switch (spot.getSpotType()) {
            case SMALL -> totalSmallSpots++;
            case MEDIUM -> totalMediumSpots++;
            case LARGE -> totalLargeSpots++;
            case XL -> totalXlSpots++;
        }
    }


    public void removeSpot(ParkingSpot spot) {
        parkingSpots.remove(spot);
        spot.setFloor(null);
    }

    public long getSmallSpotCount() {
        return parkingSpots.stream().filter(s -> s.getSpotType() == SpotType.SMALL).count();
    }

    public long getMediumSpotCount() {
        return parkingSpots.stream().filter(s -> s.getSpotType() == SpotType.MEDIUM).count();
    }

    public long getLargeSpotCount() {
        return parkingSpots.stream().filter(s -> s.getSpotType() == SpotType.LARGE).count();
    }

    public long getXLSpotCount() {
        return parkingSpots.stream().filter(s -> s.getSpotType() == SpotType.XL).count();
    }

    @Override
    public String toString() {
        return "ParkingFloor{floorId=" + floorId +
                ", floorNumber=" + floorNumber +
                ", totalSpots=" + parkingSpots.size() +
                '}';
    }

    public void setTotalSmallSpots(int totalSmallSpots) {
        this.totalSmallSpots = totalSmallSpots;
    }

    public void setTotalMediumSpots(int totalMediumSpots) {
        this.totalMediumSpots = totalMediumSpots;
    }

    public void setTotalLargeSpots(int totalLargeSpots) {
        this.totalLargeSpots = totalLargeSpots;
    }

    public void setTotalXlSpots(int totalXlSpots) {
        this.totalXlSpots = totalXlSpots;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber=floorNumber;
    }
    public void setSpots(List<ParkingSpot> spots) { this.parkingSpots = spots; }
}
