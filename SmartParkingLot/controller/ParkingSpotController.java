package com.example.SmartParkingLot.controller;

import com.example.SmartParkingLot.entity.ParkingSpot;
import com.example.SmartParkingLot.entity.SpotType;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/spots")
public class ParkingSpotController {

    private final ParkingSpotRepository spotRepo;

    public ParkingSpotController(ParkingSpotRepository spotRepo) {
        this.spotRepo = spotRepo;
    }

    @GetMapping("/available")
    public Map<SpotType, Long> availableCounts() {
        return List.of(SpotType.values()).stream()
                .collect(Collectors.toMap(
                        t -> t,
                        t -> (long) spotRepo.findBySpotTypeAndOccupied(t, false).size()
                ));
    }

    @GetMapping("/available/list")
    public List<ParkingSpot> listAvailableByType(@RequestParam(required = false) SpotType type) {
        if (type != null) return spotRepo.findBySpotTypeAndOccupied(type, false);
        return spotRepo.findAll().stream().filter(s -> !s.isOccupied()).collect(Collectors.toList());
    }
}
