package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.entity.*;
import com.example.SmartParkingLot.repository.ParkingFloorRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitializationService {

    private final ParkingFloorRepository floorRepo;
    private final ParkingSpotRepository spotRepo;

    public InitializationService(ParkingFloorRepository floorRepo, ParkingSpotRepository spotRepo) {
        this.floorRepo = floorRepo;
        this.spotRepo = spotRepo;
    }

    @PostConstruct
    public void init() {
        for (int f = 1; f <= 3; f++) {
            ParkingFloor floor = new ParkingFloor();
            floor.setFloorNumber(f);
            floorRepo.save(floor);

            List<ParkingSpot> spots = new ArrayList<>();
            for (int i = 1; i <= 12; i++) { // 12 spots per floor now
                ParkingSpot spot = new ParkingSpot();
                spot.setFloor(floor);
                spot.setSpotNumber(i);

                if (i <= 3) spot.setSpotType(SpotType.SMALL);
                else if (i <= 7) spot.setSpotType(SpotType.MEDIUM);
                else if (i <= 10) spot.setSpotType(SpotType.LARGE);
                else spot.setSpotType(SpotType.XL); // last 2 are XL

                spotRepo.save(spot);
                spots.add(spot);
            }
            floor.setSpots(spots);
            floorRepo.save(floor);
        }
    }

}
