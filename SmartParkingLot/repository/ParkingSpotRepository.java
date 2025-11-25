package com.example.SmartParkingLot.repository;

import com.example.SmartParkingLot.entity.ParkingSpot;
import com.example.SmartParkingLot.entity.SpotType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    List<ParkingSpot> findBySpotTypeAndOccupied(SpotType spotType, boolean occupied);

    // Find all spots on a floor
    List<ParkingSpot> findByFloorFloorId(Long floorId);

    // PESSIMISTIC lock — return list but we will pass Pageable to limit to 1
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ParkingSpot p WHERE p.spotType = :type AND p.occupied = false ORDER BY p.floor.floorNumber ASC, p.spotNumber ASC")
    List<ParkingSpot> findAvailableForUpdate(@Param("type") SpotType type, Pageable pageable);
    ParkingSpot findFirstByOccupiedFalseAndSpotTypeOrderByFloorFloorNumberAscSpotNumberAsc(SpotType spotType);

    Arrays findBySpotTypeAndOccupiedFalse(SpotType spotType);
}
