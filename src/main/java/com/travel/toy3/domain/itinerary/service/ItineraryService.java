package com.travel.toy3.domain.itinerary.service;

import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.dto.CreateMoving;
import com.travel.toy3.domain.itinerary.dto.CreateAccommodation;
import com.travel.toy3.domain.itinerary.dto.CreateStay;
import com.travel.toy3.domain.itinerary.dto.ItineraryDTO;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.entity.Accommodation;
import com.travel.toy3.domain.itinerary.entity.Stay;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import com.travel.toy3.domain.itinerary.repository.MovingRepository;
import com.travel.toy3.domain.itinerary.repository.AccommodationRepository;
import com.travel.toy3.domain.itinerary.repository.StayRepository;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItineraryService {
    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private MovingRepository movingRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private StayRepository stayRepository;

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

    @Transactional
    public CreateAccommodation.Response addAccommodation(Long tripId, CreateAccommodation.Request request) {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateAccommodation.Response.fromEntity(itineraryRepository.save(itinerary), accommodationRepository.save(addAccommodationFromRequest(itinerary, request)));
    }

    private Accommodation addAccommodationFromRequest(Itinerary itinerary, CreateAccommodation.Request request) {
        return Accommodation.builder()
                .itinerary(itinerary)
                .accommodationPlaceName(request.getAccommodationPlaceName())
//                .accommodationPlaceLatitude()
//                .accommodationPlaceLongitude()
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .build();
    }

    @Transactional
    public CreateStay.Response addStay(Long tripId, CreateStay.Request request) {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateStay.Response.fromEntity(itineraryRepository.save(itinerary), stayRepository.save(addStayFromRequest(itinerary, request)));
    }

    private Stay addStayFromRequest(Itinerary itinerary, CreateStay.Request request) {
        return Stay.builder()
                .itinerary(itinerary)
                .stayPlaceName(request.getStayPlaceName())
//                .stayPlaceLatitude()
//                .stayPlaceLongitude()
                .arrivalDatetime(request.getArrivalDatetime())
                .departureDatetime(request.getDepartureDatetime())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ItineraryDTO> getAllItineraries(Long tripId) {
        List<Itinerary> itineraries = itineraryRepository.findByTrip_Id(tripId);
        List<ItineraryDTO> itineraryDTOS = new ArrayList<>();

        for (Itinerary itinerary : itineraries) {
            if (itinerary.getItineraryType() == ItineraryType.MOVING) {
                itineraryDTOS.add(ItineraryDTO.fromEntity(itinerary, movingRepository.findByItinerary_Id(itinerary.getId())));
            }
        }

        return itineraryDTOS;
    }
}
