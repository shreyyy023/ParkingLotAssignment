package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.dto.ParkingSessionDTO;
import com.example.SmartParkingLot.entity.*;
import com.example.SmartParkingLot.repository.ParkingSessionRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import com.example.SmartParkingLot.repository.VehicleRepository;
import com.example.SmartParkingLot.util.RateCard;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingSessionService {

    private final ParkingSessionRepository sessionRepo;
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;
    private final AllocationService allocationService;

    public ParkingSessionService(ParkingSessionRepository sessionRepo,
                                 ParkingSpotRepository spotRepo,
                                 VehicleRepository vehicleRepo,
                                 AllocationService allocationService) {
        this.sessionRepo = sessionRepo;
        this.spotRepo = spotRepo;
        this.vehicleRepo = vehicleRepo;
        this.allocationService = allocationService;
    }

    /**
     * Park vehicle: concurrency-safe. Returns DTO of session created.
     */
    @Transactional
    public ParkingSessionDTO park(String registrationNumber, VehicleType vehicleType) {
        // 1. prevent double parking
        sessionRepo.findByVehicle_RegistrationNumberAndExitTimeIsNull(registrationNumber)
                .ifPresent(s -> { throw new IllegalStateException("Vehicle already parked"); });

        // 2. decide allowed spot types ordered by preference
        var allowed = allowedSpotTypes(vehicleType);

        ParkingSpot chosen = allowed.stream().map(allocationService::findAndLockAvailableSpot).filter(Optional::isPresent).findFirst().map(Optional::get).orElse(null);
        // attempt to find and lock one row
        if (chosen == null) throw new IllegalStateException("No available spot for vehicle type: " + vehicleType);

        // 3. create or fetch vehicle
        Vehicle vehicle = vehicleRepo.findByRegistrationNumber(registrationNumber)
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setRegistrationNumber(registrationNumber);
                    v.setVehicleType(vehicleType);
                    v.setRequiredSpotType(chosen.getSpotType());
                    return vehicleRepo.save(v);
                });

        // 4. mark spot occupied and create session
        chosen.setOccupied(true);
        spotRepo.save(chosen);

        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setParkingSpot(chosen);
        session.setEntryTime(LocalDateTime.now());
        sessionRepo.save(session);

        // keep the link
        chosen.setCurrentSession(session);
        spotRepo.save(chosen);

        // prepare DTO
        return new ParkingSessionDTO(session.getSessionId(),
                vehicle.getRegistrationNumber(),
                chosen.getSpotNumber(),
                chosen.getFloor().getFloorNumber(),
                chosen.getSpotType(),
                session.getEntryTime(),
                session.getExitTime(),
                session.getTotalMinutes(),
                session.getTotalCost());
    }

    /**
     * Exit vehicle: end session, compute cost, free spot.
     */
    @Transactional
    public ParkingSessionDTO exit(String registrationNumber) {
        ParkingSession session = sessionRepo
                .findByVehicle_RegistrationNumberAndExitTimeIsNull(registrationNumber)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not parked"));

        int rate = RateCard.getRate(session.getVehicle().getVehicleType()
        );
        session.endSession(LocalDateTime.now(), rate);

        // free spot
        ParkingSpot spot = session.getParkingSpot();
        spot.setCurrentSession(null);
        spot.setOccupied(false);
        spotRepo.save(spot);

        sessionRepo.save(session);

        return new ParkingSessionDTO(session.getSessionId(),
                registrationNumber,
                spot.getSpotNumber(),
                spot.getFloor().getFloorNumber(),
                spot.getSpotType(),
                session.getEntryTime(),
                session.getExitTime(),
                session.getTotalMinutes(),
                session.getTotalCost());
    }

    private java.util.List<SpotType> allowedSpotTypes(VehicleType vehicleType) {
        return switch (vehicleType) {
            case BIKE -> java.util.List.of(SpotType.SMALL, SpotType.MEDIUM, SpotType.LARGE, SpotType.XL);
            case CAR -> java.util.List.of(SpotType.MEDIUM, SpotType.LARGE, SpotType.XL);
            case BUS -> java.util.List.of(SpotType.LARGE, SpotType.XL);
            case TRUCK -> java.util.List.of(SpotType.XL);
        };
    }
}
