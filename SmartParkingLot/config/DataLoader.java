package com.example.SmartParkingLot.config;

import com.example.SmartParkingLot.entity.*;
import com.example.SmartParkingLot.repository.ParkingFloorRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Seeds DB with sample floors and spots on startup (H2 / dev).
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ParkingFloorRepository floorRepo;
    private final ParkingSpotRepository spotRepo;

    public DataLoader(ParkingFloorRepository floorRepo, ParkingSpotRepository spotRepo) {
        this.floorRepo = floorRepo;
        this.spotRepo = spotRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (floorRepo.count() > 0) return; // already seeded

        // Example: 2 floors
        var f1 = new ParkingFloor(1);
        f1.setTotalSmallSpots(5);
        f1.setTotalMediumSpots(5);
        f1.setTotalLargeSpots(2);
        f1.setTotalXlSpots(1);

        var f2 = new ParkingFloor(2);
        f2.setTotalSmallSpots(3);
        f2.setTotalMediumSpots(4);
        f2.setTotalLargeSpots(2);
        f2.setTotalXlSpots(1);

        floorRepo.save(f1);
        floorRepo.save(f2);

        // create spots for f1
        createSpotsForFloor(f1);
        createSpotsForFloor(f2);
    }

    private void createSpotsForFloor(ParkingFloor floor) {
        List<ParkingSpot> list = new ArrayList<>();
        int num = 1;
        for (int i = 0; i < floor.getSmallSpotCount(); i++) {
            var s = new ParkingSpot();
            s.setSpotNumber(num++);
            s.setSpotType(SpotType.SMALL);
            s.setOccupied(false);
            s.setFloor(floor);
            list.add(s);
        }
        for (int i = 0; i < floor.getMediumSpotCount(); i++) {
            var s = new ParkingSpot();
            s.setSpotNumber(num++);
            s.setSpotType(SpotType.MEDIUM);
            s.setOccupied(false);
            s.setFloor(floor);
            list.add(s);
        }
        for (int i = 0; i < floor.getLargeSpotCount(); i++) {
            var s = new ParkingSpot();
            s.setSpotNumber(num++);
            s.setSpotType(SpotType.LARGE);
            s.setOccupied(false);
            s.setFloor(floor);
            list.add(s);
        }
        for (int i = 0; i < floor.getXLSpotCount(); i++) {
            var s = new ParkingSpot();
            s.setSpotNumber(num++);
            s.setSpotType(SpotType.XL);
            s.setOccupied(false);
            s.setFloor(floor);
            list.add(s);
        }
        spotRepo.saveAll(list);
    }
}
