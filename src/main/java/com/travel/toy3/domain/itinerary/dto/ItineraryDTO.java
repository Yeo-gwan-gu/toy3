package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ItineraryDTO {
    private Long tripId;
    private Long itineraryId;
    private ItineraryType itineraryType;
    private String itineraryName;
    private MovingDTO moving;
//    private AccommodationDTO accommodationDTO;
//    private StayDTO stayDTO;

    public static ItineraryDTO fromMovingEntity(@NonNull Itinerary itinerary, @NonNull Moving moving) {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .moving(MovingDTO.builder()
                        .vehicle(moving.getVehicle())
                        .departurePlace(moving.getDeparturePlace())
                        .destinationPlace(moving.getDestinationPlace())
//                        .departurePlaceAddress()
//                        .destinationPlaceAddress()
                        .departureDatetime(moving.getDepartureDatetime())
                        .arrivalDatetime(moving.getArrivalDatetime())
                        .build())
                .build();
    }
}
