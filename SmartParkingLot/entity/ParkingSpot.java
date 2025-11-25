package com.example.SmartParkingLot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_spots", indexes = {
        @Index(name = "idx_spot_type_occupied", columnList = "spotType, occupied")
})
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;

    @Column(nullable = false)
    private int spotNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpotType spotType;

    @Column(nullable = false)
    private boolean occupied = false;

    @OneToOne
    @JoinColumn(name = "current_session_id")
    @JsonIgnore
    private ParkingSession currentSession;


    @ManyToOne(optional = false)
    @JoinColumn(name = "floor_id")
    @JsonBackReference
    private ParkingFloor floor;

    public ParkingSpot() {}

    public ParkingSpot(int spotNumber, SpotType spotType, ParkingFloor floor) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
        setFloor(floor);
    }


    public Long getSpotId() {
        return spotId;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public ParkingSession getCurrentSession() {
        return currentSession;
    }

    public ParkingFloor getFloor() {
        return floor;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public void assignSession(ParkingSession session) {
        this.currentSession = session;
    }


    @Override
    public String toString() {
        return "ParkingSpot{spotId=" + spotId +
                ", number=" + spotNumber +
                ", type=" + spotType +
                ", occupied=" + occupied +
                '}';
    }

    public void setFloor(ParkingFloor floor) {
        this.floor = floor;
    }




    public void setSpotNumber(int number) {
        this.spotNumber=number;
    }

    public void setSpotType(SpotType type) {
        this.spotType=type;
    }

    public void setCurrentSession(ParkingSession session) {
        this.currentSession = session;
        this.occupied = (session != null);
    }


}
