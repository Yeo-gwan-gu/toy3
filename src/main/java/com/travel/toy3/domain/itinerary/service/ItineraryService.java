package com.travel.toy3.domain.itinerary.service;

import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ItineraryService {
    @Autowired
    private ItineraryRepository itineraryRepository;

//    @Autowired
//    private TripRepository tripRepository;

    @Transactional
    public CreateItinerary.Response addItinerary(CreateItinerary.Request request) {
//    public CreateItinerary.Response addItinerary(Long tripId, CreateItinerary.Request request) {
//        return CreateItinerary.Response.fromEntity(itineraryRepository.save(addItineraryFromRequest(tripId, request)));
        return CreateItinerary.Response.fromEntity(itineraryRepository.save(addItineraryFromRequest(request)));
    }

//    public Itinerary addItineraryFromRequest(Long tripId, CreateItinerary.Request request) {
    public Itinerary addItineraryFromRequest(CreateItinerary.Request request) {

//        var optionalTrip = tripRepository.findById(tripId);
//        Trip trip = optionalTrip.orElseThrow(() -> RuntimeException("여행 아이디 없당"));

        return Itinerary.builder()
//                .trip(trip)
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();
    }
}
