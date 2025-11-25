package com.example.SmartParkingLot.controller;

import com.example.SmartParkingLot.dto.FloorRequest;
import com.example.SmartParkingLot.dto.ParkingFloorDTO;
import com.example.SmartParkingLot.service.ParkingFloorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/floors")
public class ParkingFloorController {

    private final ParkingFloorService floorService;

    public ParkingFloorController(ParkingFloorService floorService) {
        this.floorService = floorService;
    }

    @PostMapping
    public ResponseEntity<ParkingFloorDTO> createFloor(@RequestBody FloorRequest request) {
        ParkingFloorDTO createdFloor = floorService.createFloor(request);
        return new ResponseEntity<>(createdFloor, HttpStatus.CREATED);
    }

    @GetMapping("/{floorNumber}")
    public ResponseEntity<ParkingFloorDTO> getFloor(@PathVariable int floorNumber) {
        ParkingFloorDTO floor = floorService.getFloorByNumber(floorNumber); // Implement in service
        return ResponseEntity.ok(floor);
    }
}
