package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.entity.Vehicle;
import com.example.SmartParkingLot.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle registerVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleByRegNumber(String regNumber) {
        return vehicleRepository.findByRegistrationNumber(regNumber)
                .orElse(null);
    }
}
