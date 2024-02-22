package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Moving;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CreateMoving {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private String vehicle;
        @NotNull
        private String departurePlace;
        @NotNull
        private String destinationPlace;
        @NotNull
        private String departurePlaceAddress; // 출발지 주소
        @NotNull
        private String destinationPlaceAddress; // 도착지 주소
        @NotNull
        private LocalDateTime departureDatetime;
        @NotNull
        private LocalDateTime arrivalDatetime;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Response {
            private Long tripId;
            private Long itineraryId;
            private String vehicle;
            private String departurePlace;
            private String destinationPlace;
            private String departurePlaceAddress; // 출발지 주소
            private String destinationPlaceAddress; // 도착지 주소
            private LocalDateTime departureDatetime;
            private LocalDateTime arrivalDatetime;

            public static Response fromEntity(@NonNull Moving moving) {
                return Response.builder()
//                        .tripId(moving.getItinerary().getTrip().getId())
                        .itineraryId(moving.getItinerary().getId())
                        .vehicle(moving.getVehicle())
                        .departurePlace(moving.getDeparturePlace())
                        .destinationPlace(moving.getDestinationPlace())
//                        .departurePlaceAddress()
//                        .destinationPlaceAddress()
                        .departureDatetime(moving.getDepartureDatetime())
                        .arrivalDatetime(moving.getArrivalDatetime())
                        .build();
            }
        }
    }
}
