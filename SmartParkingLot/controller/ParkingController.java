package com.example.SmartParkingLot.controller;

import com.example.SmartParkingLot.entity.*;
import com.example.SmartParkingLot.repository.ParkingSessionRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import com.example.SmartParkingLot.repository.VehicleRepository;
import com.example.SmartParkingLot.util.RateCard;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingSpotRepository spotRepo;
    private final ParkingSessionRepository sessionRepo;
    private final VehicleRepository vehicleRepo;

    public ParkingController(ParkingSpotRepository spotRepo,
                             ParkingSessionRepository sessionRepo,
                             VehicleRepository vehicleRepo) {
        this.spotRepo = spotRepo;
        this.sessionRepo = sessionRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @PostMapping("/park")
    public ResponseEntity<?> park(@RequestParam String vehicleNumber,
                                  @RequestParam String vehicleType) {

        VehicleType type = VehicleType.valueOf(vehicleType.toUpperCase());
        SpotType requiredSpotType = type.getRequiredSpotType(); // ensures requiredSpotType is never null

        // 1. Find a free spot
        List<ParkingSpot> spots = spotRepo.findBySpotTypeAndOccupied(requiredSpotType, false);
        if (spots.isEmpty())
            return ResponseEntity.badRequest().body("No free spots for " + requiredSpotType);

        ParkingSpot spot = spots.get(0);

        // 2. Create vehicle if not exists
        Vehicle vehicle = vehicleRepo.findByRegistrationNumber(vehicleNumber)
                .orElseGet(() -> {
                    Vehicle v = new Vehicle();
                    v.setRegistrationNumber(vehicleNumber);
                    v.setVehicleType(type);
                    v.setRequiredSpotType(requiredSpotType); // crucial
                    return vehicleRepo.save(v);
                });

        // 3. Create parking session
        ParkingSession session = new ParkingSession(vehicle, spot, LocalDateTime.now());
        sessionRepo.save(session);

        // 4. Mark spot as occupied
        spot.setOccupied(true);
        spot.setCurrentSession(session);
        spotRepo.save(spot);

        return ResponseEntity.ok("Vehicle parked at spot " + spot.getSpotNumber());
    }

    @PostMapping("/unpark")
    public ResponseEntity<?> unpark(@RequestParam String vehicleNumber) {

        ParkingSession session = sessionRepo
                .findByVehicle_RegistrationNumberAndExitTimeIsNull(vehicleNumber)
                .orElseThrow(() -> new RuntimeException("Vehicle is not parked"));

        int rate = RateCard.getRate(session.getVehicle().getVehicleType());
        session.endSession(LocalDateTime.now(), rate);

        sessionRepo.save(session);

        ParkingSpot spot = session.getParkingSpot();
        spot.setOccupied(false);
        spot.setCurrentSession(null);
        spotRepo.save(spot);

        return ResponseEntity.ok("Vehicle unparked. Total cost = ₹" + session.getTotalCost());
    }
}
