package com.travel.toy3.domain.itinerary.service;

import com.travel.toy3.domain.itinerary.dto.*;
import com.travel.toy3.domain.itinerary.entity.Accommodation;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.entity.Stay;
import com.travel.toy3.domain.itinerary.repository.AccommodationRepository;
import com.travel.toy3.domain.itinerary.repository.ItineraryRepository;
import com.travel.toy3.domain.itinerary.repository.MovingRepository;
import com.travel.toy3.domain.itinerary.repository.StayRepository;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.util.Geocoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    public CreateMoving.Response addMoving(Long tripId, CreateMoving.Request request) throws IOException {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateMoving.Response.fromEntity(itineraryRepository.save(itinerary), movingRepository.save(addMovingFromRequest(itinerary, request)));
    }

    private Moving addMovingFromRequest(Itinerary itinerary, CreateMoving.Request request) throws IOException {
        return Moving.builder()
                .itinerary(itinerary)
                .vehicle(request.getVehicle())
                .departurePlace(request.getDeparturePlace())
                .destinationPlace(request.getDestinationPlace())
                .departurePlaceLatitude(Geocoding.getGeoInfo(request.getDeparturePlaceAddress()).getLatitude())
                .departurePlaceLongitude(Geocoding.getGeoInfo(request.getDeparturePlaceAddress()).getLongitude())
                .destinationPlaceLatitude(Geocoding.getGeoInfo(request.getDestinationPlaceAddress()).getLatitude())
                .destinationPlaceLongitude(Geocoding.getGeoInfo(request.getDestinationPlaceAddress()).getLongitude())
                .departureDatetime(request.getDepartureDatetime())
                .arrivalDatetime(request.getArrivalDatetime())
                .build();
    }

    @Transactional
    public CreateAccommodation.Response addAccommodation(Long tripId, CreateAccommodation.Request request) throws IOException {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateAccommodation.Response.fromEntity(itineraryRepository.save(itinerary), accommodationRepository.save(addAccommodationFromRequest(itinerary, request)));
    }

    private Accommodation addAccommodationFromRequest(Itinerary itinerary, CreateAccommodation.Request request) throws IOException {
        return Accommodation.builder()
                .itinerary(itinerary)
                .accommodationPlaceName(request.getAccommodationPlaceName())
                .accommodationPlaceLatitude(Geocoding.getGeoInfo(request.getAccommodationPlaceAddress()).getLatitude())
                .accommodationPlaceLongitude(Geocoding.getGeoInfo(request.getAccommodationPlaceAddress()).getLongitude())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .build();
    }

    @Transactional
    public CreateStay.Response addStay(Long tripId, CreateStay.Request request) throws IOException {
        CreateItinerary.Request itineraryRequest = CreateItinerary.Request.builder()
                .itineraryType(request.getItineraryType())
                .itineraryName(request.getItineraryName())
                .build();

        Itinerary itinerary = addItineraryFromRequest(tripId, itineraryRequest);

        return CreateStay.Response.fromEntity(itineraryRepository.save(itinerary), stayRepository.save(addStayFromRequest(itinerary, request)));
    }

    private Stay addStayFromRequest(Itinerary itinerary, CreateStay.Request request) throws IOException {
        return Stay.builder()
                .itinerary(itinerary)
                .stayPlaceName(request.getStayPlaceName())
                .stayPlaceLatitude(Geocoding.getGeoInfo(request.getStayPlaceAddress()).getLatitude())
                .stayPlaceLongitude(Geocoding.getGeoInfo(request.getStayPlaceAddress()).getLongitude())
                .arrivalDatetime(request.getArrivalDatetime())
                .departureDatetime(request.getDepartureDatetime())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ItineraryDTO> getAllItineraries(Long tripId) throws IOException {
        List<Itinerary> itineraries = itineraryRepository.findByTrip_Id(tripId);
        List<ItineraryDTO> itineraryDTOS = new ArrayList<>();

        for (Itinerary itinerary : itineraries) {
            if (itinerary.getItineraryType() == ItineraryType.MOVING) {
                itineraryDTOS.add(ItineraryDTO.fromMovingEntity(itinerary, movingRepository.findByItinerary_Id(itinerary.getId())));
            } else if (itinerary.getItineraryType() == ItineraryType.ACCOMMODATION) {
                itineraryDTOS.add(ItineraryDTO.fromAccommodationEntity(itinerary, accommodationRepository.findByItinerary_Id(itinerary.getId())));
            } else {
                itineraryDTOS.add(ItineraryDTO.fromStayEntity(itinerary, stayRepository.findByItinerary_Id(itinerary.getId())));
            }
        }

        return itineraryDTOS;
    }

    @Transactional(readOnly = true)
    public ItineraryDTO getItineraryById(Long itineraryId) throws IOException {
        var optionalItinerary = itineraryRepository.findById(itineraryId);
        Itinerary itinerary = optionalItinerary
                .orElseThrow(
                        () -> new CustomException(CustomErrorCode.INVALID_ITINERARY)
                );

        if (itinerary.getItineraryType() == ItineraryType.MOVING) {
            return ItineraryDTO.fromMovingEntity(itinerary, movingRepository.findByItinerary_Id(itineraryId));
        } else if (itinerary.getItineraryType() == ItineraryType.ACCOMMODATION) {
            return ItineraryDTO.fromAccommodationEntity(itinerary, accommodationRepository.findByItinerary_Id(itineraryId));
        } else {
            return ItineraryDTO.fromStayEntity(itinerary, stayRepository.findByItinerary_Id(itineraryId));
        }
    }
}
