package com.travel.toy3.domain.itinerary.service;

import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItineraryService {
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private TripRepository tripRepository;

    @Transactional
    public CreateItinerary.Response addItinerary(Long tripId, CreateItinerary.Request request) {
        return CreateItinerary.Response.fromEntity(itineraryRepository.save(addItineraryFromRequest(tripId, request)));
    }

    public Itinerary addItineraryFromRequest(Long tripId, CreateItinerary.Request request) {
        var optionalTrip = tripRepository.findById(tripId);
        Trip trip = optionalTrip.orElseThrow(() -> new CustomException(CustomErrorCode.INVALID_TRIP));

        return Itinerary.builder()
                .trip(trip)
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();
    }
}
