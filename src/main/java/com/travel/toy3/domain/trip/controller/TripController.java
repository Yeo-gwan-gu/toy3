package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.trip.dto.CreateUpdateTrip;
import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.dto.TripDetailDTO;
import com.travel.toy3.domain.trip.service.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    @PostMapping
    public CreateUpdateTrip.Response addTrip(
            @RequestBody final CreateUpdateTrip.Request request
    ) {
        log.info("request : {}", request);
        return tripService.addTrip(request);
    }
    @GetMapping
    public List<TripDTO> getAllTrips() {
        log.info("===== 여행 전체 조회 ======");
        return tripService.getAllTrips();
    }

    @GetMapping("/{trip-id}")
    public TripDetailDTO getTripDetail(@PathVariable("trip-id") Long tripId) {
        log.info("===== 여행 상세 조회 ======");
        return tripService.getTripDetail(tripId);
    }
    //500 에러 나고 있음
    @GetMapping("/destination/{trip-destination}")
    public List<CreateUpdateTrip.Response> getDestinationTrip(
            @PathVariable("trip-destination") final String destination
    ) {
        log.info("===== 특정 여행지로 조회 ======");
        return tripService.getTripDestination(destination);
    }

    @GetMapping("/writer/{member-username}")
    public Optional<List<CreateUpdateTrip.Response>> getUsernameTrip(@PathVariable("member-username") String username) {
        log.info("===== 사용자로 조회 ======");
        return tripService.getUsernameTrip(username);
    }

}
