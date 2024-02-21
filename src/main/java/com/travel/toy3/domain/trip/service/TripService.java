package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.dto.TripDetailDTO;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.travel.toy3.exception.CustomErrorCode.INVALID_TRIP;

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
    // tripId를 찾는 함수
    private Trip getByTripId(Long tripId) {
        Optional<Trip> optional = tripRepository.findById(tripId);

        if (optional.isPresent()) {
            Trip trip = optional.get();
            return trip;
        } else
            throw new RuntimeException();
    }

    @Transactional
    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll()
                .stream().map((Trip trip) -> TripDTO.fromEntity(trip))
                .collect(Collectors.toList());
    }

    //여행 상세 조회
    @Transactional
    public TripDetailDTO getTripDetail(Long tripId) {
        Trip trip = tripRepository.getById(tripId);
        return TripDetailDTO.fromEntity(trip);
    }

    //목적지로 검색
    @Transactional
    public List<CreateUpdateTrip.Response> getTripDestination(String destination) {
        List<Trip> trips = getByDestination(destination);
        List<CreateUpdateTrip.Response> tripList = new ArrayList<>();

        for (Trip trip : trips) {
            tripList.add(CreateUpdateTrip.Response.fromEntity(trip));
        }
        return tripList;
    }

    private List<Trip> getByDestination(String destination) {
        Optional<List<Trip>> optional = tripRepository.findByTripDestination(destination);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new CustomException(INVALID_TRIP);
        }
    }

    //trip이 아니라 comment로 해야 할 것 같은데
    @Transactional
    public Optional<List<CreateUpdateTrip.Response>> getUsernameTrip(String username) {
        Optional<List<Trip>> trips = tripRepository.findByTripDestination(username);

        List<CreateUpdateTrip.Response> tripList = new ArrayList<>();
        for (Trip trip : trips.get()) {
            tripList.add(CreateUpdateTrip.Response.fromEntity(trip));
        }
        return Optional.ofNullable(tripList);
    }

    @Transactional
    public CreateUpdateTrip.Response updateTrip(
            //Long memberId,
            Long tripId,
            CreateUpdateTrip.Request request
    ) {
        Trip existingTrip = getByTripId(tripId);

        // 여행 정보가 존재하는지 확인
        updateTripFromRequest(existingTrip, request);
        return CreateUpdateTrip.Response.fromEntity(existingTrip);
    }

    private void updateTripFromRequest(Trip trip, CreateUpdateTrip.Request request) {
        trip.setTripName(request.getTripName());
        trip.setTripDepartureDate(request.getTripDepartureDate());
        trip.setTripArrivalDate(request.getTripArrivalDate());
        trip.setTripDestination(request.getTripDestination());
        trip.setIsDomestic(request.getIsDomestic());
    }
}
