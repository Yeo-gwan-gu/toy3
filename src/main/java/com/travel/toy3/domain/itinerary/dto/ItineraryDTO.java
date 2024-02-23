package com.travel.toy3.domain.itinerary.dto;

import com.travel.toy3.domain.itinerary.entity.Accommodation;
import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.itinerary.entity.Moving;
import com.travel.toy3.domain.itinerary.entity.Stay;
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
    private AccommodationDTO accommodation;
    private StayDTO stay;

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

    public static ItineraryDTO fromAccommodationEntity(@NonNull Itinerary itinerary, @NonNull Accommodation accommodation) {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .accommodation(AccommodationDTO.builder()
                        .accommodationPlaceName(accommodation.getAccommodationPlaceName())
//                        .accommodationPlaceAddress()
                        .checkIn(accommodation.getCheckIn())
                        .checkOut(accommodation.getCheckOut())
                        .build())
                .build();
    }

    public static ItineraryDTO fromStayEntity(@NonNull Itinerary itinerary, @NonNull Stay stay) {

        return ItineraryDTO.builder()
                .tripId(itinerary.getTrip().getId())
                .itineraryId(itinerary.getId())
                .itineraryType(itinerary.getItineraryType())
                .itineraryName(itinerary.getItineraryName())
                .stay(StayDTO.builder()
                        .stayPlaceName(stay.getStayPlaceName())
//                        .stayPlaceAddress()
                        .arrivalDatetime(stay.getArrivalDatetime())
                        .departureDatetime(stay.getDepartureDatetime())
                        .build())
                .build();
    }
}
