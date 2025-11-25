package com.example.SmartParkingLot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Keep only ONE of these for IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArg(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    // Remove or comment this
    // @ExceptionHandler(IllegalArgumentException.class)
    // public ResponseEntity<String> badRequest(IllegalArgumentException e) {
    //     return ResponseEntity.badRequest().body("Bad request: " + e.getMessage());
    // }

    // You can still handle other exceptions separately
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAll(Exception e) {
        return ResponseEntity.status(500).body("Internal server error: " + e.getMessage());
    }
}


