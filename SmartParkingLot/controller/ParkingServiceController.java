//package com.example.SmartParkingLot.controller;
//
//import com.example.SmartParkingLot.dto.ParkingSessionDTO;
//import com.example.SmartParkingLot.entity.VehicleType;
//import com.example.SmartParkingLot.service.ParkingService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/parking")
//public class ParkingServiceController {
//
//    private final ParkingService parkingService;
//
//    public ParkingServiceController(ParkingService parkingService) {
//        this.parkingService = parkingService;
//    }
//
//    @PostMapping("/park")
//    public ResponseEntity<ParkingSessionDTO> parkVehicle(
//            @RequestParam String vehicleNumber,
//            @RequestParam VehicleType vehicleType) {
//
//        ParkingSessionDTO sessionDTO = parkingService.parkVehicle(vehicleNumber, vehicleType);
//        return new ResponseEntity<>(sessionDTO, HttpStatus.CREATED);
//    }
//
//
//    @PostMapping("/exit/{vehicleNumber}")
//    public ResponseEntity<ParkingSessionDTO> exitVehicle(@PathVariable String vehicleNumber) {
//        return ResponseEntity.ok(parkingService.exitVehicle(vehicleNumber));
//    }
//}
