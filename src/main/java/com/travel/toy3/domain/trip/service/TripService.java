package com.travel.toy3.domain.trip.service;

import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.dto.TripDetailDTO;
import com.travel.toy3.domain.trip.entity.Comment;
import com.travel.toy3.domain.trip.entity.Trip;
import com.travel.toy3.domain.trip.repository.CommentRepository;
import com.travel.toy3.domain.trip.repository.LikeRepository;
import com.travel.toy3.domain.trip.repository.TripRepository;
import com.travel.toy3.exception.CustomException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.travel.toy3.exception.CustomErrorCode.INVALID_TRIP;
import static com.travel.toy3.exception.CustomErrorCode.NO_EDIT_PERMISSION;

@Slf4j
@RequiredArgsConstructor
@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public CreateUpdateTrip.Response addTrip(
            CreateUpdateTrip.Request request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomMember customMember = (CustomMember) authentication.getPrincipal();

        Trip trip = Trip.builder()
                .member(customMember.getMember())
                .tripName(request.getTripName())
                .tripDepartureDate(request.getTripDepartureDate())
                .tripArrivalDate(request.getTripArrivalDate())
                .tripDestination(request.getTripDestination())
                .isDomestic(request.getIsDomestic())
                .build();
        Trip addTrip = tripRepository.save(trip);

        if (addTrip == null) {
            throw new CustomException(NO_EDIT_PERMISSION);
        }
        return CreateUpdateTrip.Response.fromEntity(trip);
    }


    // tripId를 찾는 함수
    public Trip getByTripId(Long tripId) {
        Optional<Trip> optional = tripRepository.findById(tripId);

        if (optional.isPresent()) {
            Trip trip = optional.get();
            return trip;
        } else
            throw new RuntimeException();
    }

    @Transactional
    public List<TripDTO> getAllTrips() {
        List<Trip> trips = tripRepository.findAll();
        return trips.stream().map(trip -> {
            Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();
            Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
            return TripDTO.fromEntity(trip,likeCount, commentCount);
        }).collect(Collectors.toList());
    }


    //여행 상세 조회
    @Transactional
    public TripDetailDTO getTripDetail(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (!optionalTrip.isPresent()) {
            throw new CustomException(INVALID_TRIP);
        }
        Trip trip = optionalTrip.get();
        List<Comment> comments = commentRepository.findByTripId(tripId);
        Integer commentCount = commentRepository.countByTripId(trip.getId()).intValue();
        Integer likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y").intValue();

        return TripDetailDTO.fromEntity(trip,likeCount, comments, commentCount);
    }

    //목적지로 검색
    @Transactional
    public List<CreateUpdateTrip.Response> getTripDestination(String destination) {
        List<Trip> trips = getByDestination(destination);
        List<CreateUpdateTrip.Response> tripList = new ArrayList<>();

        for (Trip trip : trips) {

            CreateUpdateTrip.Response tripResponse = CreateUpdateTrip.Response.fromEntity(trip);
            Long likeCount = likeRepository.countByTripIdAndStatus(trip.getId(), "Y");

            tripResponse.setLikeCount(likeCount);
            tripList.add(tripResponse);
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
