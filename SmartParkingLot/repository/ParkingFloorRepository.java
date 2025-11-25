package com.example.SmartParkingLot.repository;

import com.example.SmartParkingLot.entity.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor,Long> {
    boolean existsByFloorNumber(int floorNumber);
    Optional<ParkingFloor> findByFloorNumber(int floorNumber);
}
