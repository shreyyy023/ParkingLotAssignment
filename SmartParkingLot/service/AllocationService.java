package com.example.SmartParkingLot.service;

import com.example.SmartParkingLot.entity.ParkingSpot;
import com.example.SmartParkingLot.entity.SpotType;
import com.example.SmartParkingLot.repository.ParkingSpotRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AllocationService {

    private final ParkingSpotRepository spotRepo;

    public AllocationService(ParkingSpotRepository spotRepo) {
        this.spotRepo = spotRepo;
    }


    @Transactional
    public Optional<ParkingSpot> findAndLockAvailableSpot(SpotType requiredType) {
        // page size 1 — we only want the single best spot
        var list = spotRepo.findAvailableForUpdate(requiredType, PageRequest.of(0, 1));
        if (list == null || list.isEmpty()) return Optional.empty();
        return Optional.of(list.get(0));
    }
}
