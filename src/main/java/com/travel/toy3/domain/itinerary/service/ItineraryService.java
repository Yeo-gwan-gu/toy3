package com.travel.toy3.domain.itinerary.service;

import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.dto.CreateMoving;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import com.travel.toy3.domain.itinerary.repository.MovingRepository;
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
    private MovingRepository movingRepository;

    @Autowired
    private TripRepository tripRepository;

    public Itinerary addItineraryFromRequest(Long tripId, CreateItinerary.Request request) {
        var optionalTrip = tripRepository.findById(tripId);
        Trip trip = optionalTrip
                .orElseThrow(
                        () -> new CustomException(CustomErrorCode.INVALID_TRIP)
                );

        return Itinerary.builder()
                .trip(trip)
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();
    }

    @Transactional
    public CreateMoving.Response addMoving(Long tripId, CreateMoving.Request request) {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateMoving.Response.fromEntity(itineraryRepository.save(itinerary), movingRepository.save(addMovingFromRequest(itinerary, request)));
    }

    private Moving addMovingFromRequest(Itinerary itinerary, CreateMoving.Request request) {
        return Moving.builder()
                .itinerary(itinerary)
                .vehicle(request.getVehicle())
                .departurePlace(request.getDeparturePlace())
                .destinationPlace(request.getDestinationPlace())
//                .departurePlaceLatitude()
//                .departurePlaceLongitude()
//                .destinationPlaceLatitude()
//                .destinationPlaceLongitude()
                .departureDatetime(request.getDepartureDatetime())
                .arrivalDatetime(request.getArrivalDatetime())
                .build();
    }
}
