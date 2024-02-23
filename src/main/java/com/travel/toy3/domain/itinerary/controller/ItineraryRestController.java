package com.travel.toy3.domain.itinerary.controller;

import com.travel.toy3.domain.itinerary.dto.CreateAccommodation;
import com.travel.toy3.domain.itinerary.dto.CreateMoving;
import com.travel.toy3.domain.itinerary.dto.CreateStay;
import com.travel.toy3.domain.itinerary.service.ItineraryService;
import com.travel.toy3.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryRestController {
    @Autowired
    private ItineraryService itineraryService;

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
}
