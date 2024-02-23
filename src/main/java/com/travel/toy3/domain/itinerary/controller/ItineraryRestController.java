package com.travel.toy3.domain.itinerary.controller;

import com.travel.toy3.domain.itinerary.dto.CreateMoving;
import com.travel.toy3.domain.itinerary.dto.CreateAccommodation;
import com.travel.toy3.domain.itinerary.dto.CreateStay;
import com.travel.toy3.domain.itinerary.dto.ItineraryDTO;
import com.travel.toy3.domain.itinerary.service.ItineraryService;
import com.travel.toy3.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryRestController {
    @Autowired
    private ItineraryService itineraryService;

    // 여정 등록 - 이동
    @PostMapping("/{tripId}/moving")
    public ResponseEntity<ApiResponse<CreateMoving.Response>> addMoving(
            @PathVariable Long tripId,
            @Valid @RequestBody final CreateMoving.Request request
    ) {
        ApiResponse<CreateMoving.Response> response = ApiResponse.<CreateMoving.Response>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(itineraryService.addMoving(tripId, request))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    // 여정 등록 - 숙박
    @PostMapping("/{tripId}/accommodation")
    public ResponseEntity<ApiResponse<CreateAccommodation.Response>> addAccommodation(
            @PathVariable Long tripId,
            @Valid @RequestBody final CreateAccommodation.Request request
    ) {
        ApiResponse<CreateAccommodation.Response> response = ApiResponse.<CreateAccommodation.Response>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(itineraryService.addAccommodation(tripId, request))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    // 여정 등록 - 체류
    @PostMapping("/{tripId}/stay")
    public ResponseEntity<ApiResponse<CreateStay.Response>> addStay(
            @PathVariable Long tripId,
            @Valid @RequestBody final CreateStay.Request request
    ) {
        ApiResponse<CreateStay.Response> response = ApiResponse.<CreateStay.Response>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(itineraryService.addStay(tripId, request))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }

    // 특정 여행 id에 해당하는 전체 여정 조회
    @GetMapping("/trip/{tripId}")
    public ResponseEntity<ApiResponse<List<ItineraryDTO>>> getAllItineraries(
            @PathVariable Long tripId
    ) {
        ApiResponse<List<ItineraryDTO>> response = ApiResponse.<List<ItineraryDTO>>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(itineraryService.getAllItineraries(tripId))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }
}
