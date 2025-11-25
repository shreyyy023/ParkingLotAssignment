package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.dto.FloorRequest;
import com.example.SmartParkingLot.dto.ParkingFloorDTO;
import com.example.SmartParkingLot.dto.ParkingSpotDTO;
import com.example.SmartParkingLot.entity.ParkingFloor;
import com.example.SmartParkingLot.entity.ParkingSpot;
import com.example.SmartParkingLot.entity.SpotType;
import com.example.SmartParkingLot.repository.ParkingFloorRepository;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingFloorService {

    private final ParkingFloorRepository floorRepo;
    private final ParkingSpotRepository spotRepo;

    public ParkingFloorService(ParkingFloorRepository floorRepo,
                               ParkingSpotRepository spotRepo) {
        this.floorRepo = floorRepo;
        this.spotRepo = spotRepo;
    }

    /**
     * Creates a new parking floor with generated parking spots.
     */
    public ParkingFloorDTO createFloor(FloorRequest req) {

        validateRequest(req);                 // Step 1: Validate user input
        checkDuplicateFloor(req.getFloorNumber()); // Step 2: Prevent duplicates

        ParkingFloor floor = buildFloor(req); // Step 3: Create floor entity
        ParkingFloor savedFloor = floorRepo.save(floor);

        List<ParkingSpot> spots = generateSpots(savedFloor, req); // Step 4: Create spots
        spotRepo.saveAll(spots);

        List<ParkingSpotDTO> spotDTOs = savedFloor.getParkingSpots().stream()
                .map(s -> new ParkingSpotDTO(s.getSpotId(), s.getSpotNumber(), s.getSpotType(), s.isOccupied()))
                .toList();

        ParkingFloorDTO floorDTO = new ParkingFloorDTO(
                savedFloor.getFloorId(),
                savedFloor.getFloorNumber(),
                savedFloor.getSmallSpotCount(),
                savedFloor.getMediumSpotCount(),
                savedFloor.getLargeSpotCount(),
                savedFloor.getXLSpotCount(),
                spotDTOs
        );

        return floorDTO;
    }

    // ───────────────────────────────────────────────
    // VALIDATION
    // ───────────────────────────────────────────────

    private void validateRequest(FloorRequest req) {

        if (req.getFloorNumber() <= 0) {
            throw new IllegalArgumentException("Floor number must be positive");
        }

        if (req.getTotalSmallSpots() < 0 ||
                req.getTotalMediumSpots() < 0 ||
                req.getTotalLargeSpots() < 0 ||
                req.getTotalXlSpots() < 0) {
            throw new IllegalArgumentException("Spot counts cannot be negative");
        }

        int total = req.getTotalSmallSpots() +
                req.getTotalMediumSpots() +
                req.getTotalLargeSpots() +
                req.getTotalXlSpots();

        if (total == 0) {
            throw new IllegalArgumentException("Floor must have at least 1 spot");
        }
    }

    private void checkDuplicateFloor(int floorNumber) {
        if (floorRepo.existsByFloorNumber(floorNumber)) {
            throw new IllegalArgumentException("Floor already exists");
        }
    }

    public ParkingFloorDTO getFloorByNumber(int floorNumber) {
        ParkingFloor floor = floorRepo.findAll()
                .stream()
                .filter(f -> f.getFloorNumber() == floorNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Floor not found: " + floorNumber));

        List<ParkingSpotDTO> spots = floor.getParkingSpots().stream()
                .map(s -> new ParkingSpotDTO(s.getSpotId(), s.getSpotNumber(), s.getSpotType(), s.isOccupied()))
                .toList();

        return new ParkingFloorDTO(
                floor.getFloorId(),
                floor.getFloorNumber(),
                (int) floor.getSmallSpotCount(),
                (int) floor.getMediumSpotCount(),
                (int) floor.getLargeSpotCount(),
                (int) floor.getXLSpotCount(),
                spots
        );
    }


    // ───────────────────────────────────────────────
    // ENTITY BUILDERS
    // ───────────────────────────────────────────────

    private ParkingFloor buildFloor(FloorRequest req) {
        ParkingFloor floor = new ParkingFloor();
        floor.setFloorNumber(req.getFloorNumber());
        floor.setTotalSmallSpots(req.getTotalSmallSpots());
        floor.setTotalMediumSpots(req.getTotalMediumSpots());
        floor.setTotalLargeSpots(req.getTotalLargeSpots());
        floor.setTotalXlSpots(req.getTotalXlSpots());
        return floor;
    }

    private List<ParkingSpot> generateSpots(ParkingFloor floor, FloorRequest req) {

        List<ParkingSpot> spots = new ArrayList<>();
        int number = 1;

        number = createBatch(spots, floor, req.getTotalSmallSpots(), SpotType.SMALL, number);
        number = createBatch(spots, floor, req.getTotalMediumSpots(), SpotType.MEDIUM, number);
        number = createBatch(spots, floor, req.getTotalLargeSpots(), SpotType.LARGE, number);
        number = createBatch(spots, floor, req.getTotalXlSpots(), SpotType.XL, number);

        return spots;
    }

    private int createBatch(List<ParkingSpot> list,
                            ParkingFloor floor,
                            int count,
                            SpotType type,
                            int startNumber) {

        for (int i = 0; i < count; i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setSpotNumber(startNumber++);
            spot.setSpotType(type);
            spot.setOccupied(false);
            spot.setFloor(floor);       // MUST SET THIS
            floor.addSpot(spot);        // Maintain bidirectional mapping
            list.add(spot);
        }
        return startNumber;
    }
}
