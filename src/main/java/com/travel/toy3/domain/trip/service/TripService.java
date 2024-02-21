package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor
@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Transactional
    public CreateUpdateTrip.Response addTrip(CreateUpdateTrip.Request request) {
        return CreateUpdateTrip.Response.fromEntity(
                tripRepository.save(createTripFromRequest(request))
        );
    }
    private Trip createTripFromRequest(CreateUpdateTrip.Request request) {
        return Trip.builder()
                .tripName(request.getTripName())
                .tripDepartureDate(request.getTripDepartureDate())
                .tripArrivalDate(request.getTripArrivalDate())
                .tripDestination(request.getTripDestination())
                .isDomestic(request.getIsDomestic())
                .build();
    }
}
