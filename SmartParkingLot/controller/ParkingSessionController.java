package com.example.SmartParkingLot.controller;

import com.example.SmartParkingLot.entity.ParkingSession;
import com.example.SmartParkingLot.repository.ParkingSessionRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class ParkingSessionController {

    private final ParkingSessionRepository sessionRepo;

    public ParkingSessionController(ParkingSessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @GetMapping("/active")
    public List<ParkingSession> getActiveSessions() {
        return sessionRepo.findByExitTimeIsNull();
    }

    @GetMapping("/all")
    public List<ParkingSession> getAllSessions() {
        return sessionRepo.findAll();
    }
}
