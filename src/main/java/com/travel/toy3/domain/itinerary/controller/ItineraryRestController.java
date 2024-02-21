package com.travel.toy3.domain.itinerary.controller;

import com.travel.toy3.domain.itinerary.dto.CreateItinerary;
import com.travel.toy3.domain.itinerary.service.ItineraryService;
import com.travel.toy3.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryRestController {
    @Autowired
    private ItineraryService itineraryService;

    @PostMapping("/register")
//    @PostMapping("/{tripId}")
    public ResponseEntity<ApiResponse<CreateItinerary.Response>> addItinerary(
//            @PathVariable Long tripId,
            @Valid @RequestBody final CreateItinerary.Request request
    ) {

        ApiResponse<CreateItinerary.Response> response = ApiResponse.<CreateItinerary.Response>builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data(itineraryService.addItinerary(request))
//                .data(itineraryService.addItinerary(tripId, request))
                .build();

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(response);
    }
}
