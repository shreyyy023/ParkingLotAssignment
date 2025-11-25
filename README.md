# SmartParkingLot Backend

## Overview
A Spring Boot backend system for a smart parking lot that manages vehicle parking, spot allocation, fee calculation, and session tracking.

## Features
- Park a vehicle (assigns spot automatically based on vehicle type)
- Unpark a vehicle (calculates fee based on duration)
- Check available parking spots (count and list)
- Track active and all parking sessions
- Floors and spots are initialized automatically at startup

## Technologies
- Java 17+
- Spring Boot 3
- Spring Data JPA
- H2 Database (in-memory)
- Maven

## Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST | http://localhost:8080/parking/park?vehicleNumber=&vehicleType= | Park a vehicle |
| POST | http://localhost:8080/parking/unpark?vehicleNumber= | Exit/unpark a vehicle |
| GET | http://localhost:8080/spots/available | Get count of available spots by type |
| GET | http://localhost:8080/spots/available/list | List available spots |
| GET | http://localhost:8080/sessions/active | List active parking sessions |
| GET | http://localhost:8080/sessions/all | List all parking sessions |

## Running the Application
1. Clone the repository:
```bash
git clone https://github.com/shreyyy023/ParkingLotAssignment.git
