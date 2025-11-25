package com.example.SmartParkingLot.repository;

import com.example.SmartParkingLot.entity.ParkingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {

    Optional<ParkingSession> findByVehicle_RegistrationNumberAndExitTimeIsNull(String registrationNumber);

    List<ParkingSession> findByExitTimeIsNull();
}
