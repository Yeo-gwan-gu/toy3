package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping //trip 저장
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO trip) {
        return tripService.createTrip(trip);
    }


}
