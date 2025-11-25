package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.dto.ParkingSessionDTO;
import com.example.SmartParkingLot.entity.*;
import com.example.SmartParkingLot.repository.ParkingSessionRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import com.example.SmartParkingLot.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    private final ParkingSessionRepository sessionRepo;
    private final ParkingSpotRepository spotRepo;
    private final VehicleRepository vehicleRepo;

    public ParkingService(ParkingSessionRepository sessionRepo,
                          ParkingSpotRepository spotRepo,
                          VehicleRepository vehicleRepo) {
        this.sessionRepo = sessionRepo;
        this.spotRepo = spotRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @Transactional
    public ParkingSessionDTO parkVehicle(String vehicleNumber, VehicleType vehicleType) {

        // 1️⃣ Prevent duplicate parking
        sessionRepo.findByVehicle_RegistrationNumberAndExitTimeIsNull(vehicleNumber)
                .ifPresent(s -> { throw new IllegalStateException("Vehicle already parked"); });

        // 2️⃣ Map vehicle type to preferred spot types
        List<SpotType> preferredSpotTypes = allowedSpotTypes(vehicleType);

        ParkingSpot spot = null;

        // 3️⃣ Try to find first available spot in order of preference
        for (SpotType type : preferredSpotTypes) {
            spot = spotRepo
                    .findFirstByOccupiedFalseAndSpotTypeOrderByFloorFloorNumberAscSpotNumberAsc(type);


        }

        if (spot == null) {
            throw new IllegalStateException("No available spots for vehicle type " + vehicleType);
        }

        // 4️⃣ Create vehicle if it doesn't exist
        Vehicle vehicle = vehicleRepo.findByRegistrationNumber(vehicleNumber)
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setRegistrationNumber(vehicleNumber);
                    v.setVehicleType(vehicleType); // save vehicle type
                    v.setVehicleType(vehicleType);
                    return vehicleRepo.save(v);
                });

        // 5️⃣ Mark spot as occupied
        spot.setOccupied(true);
        spotRepo.save(spot);

        // 6️⃣ Create parking session
        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setParkingSpot(spot);
        session.setEntryTime(LocalDateTime.now());

        ParkingSession saved = sessionRepo.save(session);

        // 7️⃣ Set starting minimal cost for display
        int rate = getRatePerHour(spot.getSpotType());
        saved.setTotalMinutes(0);
        saved.setTotalCost(rate);

        // 8️⃣ Build and return DTO
        return new ParkingSessionDTO(
                saved.getSessionId(),
                vehicle.getRegistrationNumber(),
                spot.getSpotNumber(),
                spot.getFloor().getFloorNumber(),
                spot.getSpotType(),
                saved.getEntryTime(),
                saved.getExitTime(),
                saved.getTotalMinutes(),
                saved.getTotalCost()
        );
    }


    // Allowed spot types by vehicle type in order of preference
    private List<SpotType> allowedSpotTypes(VehicleType vehicleType) {
        return switch(vehicleType) {
            case BIKE -> List.of(SpotType.SMALL, SpotType.MEDIUM, SpotType.LARGE, SpotType.XL);
            case CAR -> List.of(SpotType.MEDIUM, SpotType.LARGE, SpotType.XL);
            case BUS -> List.of(SpotType.LARGE, SpotType.XL);
            case TRUCK -> List.of(SpotType.XL);
        };
    }

    private int getRatePerHour(SpotType spotType) {
        return switch (spotType) {
            case SMALL -> 20;
            case MEDIUM -> 40;
            case LARGE -> 100;
            case XL -> 200;
        };
    }



    @Transactional
    public ParkingSessionDTO exitVehicle(String vehicleNumber) {

        // 1️⃣ Find active session
        ParkingSession session = sessionRepo
                .findByVehicle_RegistrationNumberAndExitTimeIsNull(vehicleNumber)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not parked"));

        // 2️⃣ End session and calculate cost automatically
        int rate = getRatePerHour(session.getParkingSpot().getSpotType());
        session.endSession(LocalDateTime.now(), rate);



        // 3️⃣ Free the spot
        ParkingSpot spot = session.getParkingSpot();
        spot.setOccupied(false);
        spot.setCurrentSession(null);
        spotRepo.save(spot);

        // 4️⃣ Save session
        ParkingSession updated = sessionRepo.save(session);

        // 5️⃣ Return DTO
        return new ParkingSessionDTO(
                updated.getSessionId(),
                vehicleNumber,
                spot.getSpotNumber(),
                spot.getFloor().getFloorNumber(),
                spot.getSpotType(),
                updated.getEntryTime(),
                updated.getExitTime(),
                updated.getTotalMinutes(),
                updated.getTotalCost()
        );
    }


}

