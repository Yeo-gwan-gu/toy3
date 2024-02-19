package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    public ResponseEntity<TripDTO> createTrip(TripDTO trip) {
        return tripRepository.creteTrip(trip);
    }
}
