package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {

    private Long tripId;
    private Long memberId;
    private String tripName;
    private LocalDate tripDepartureDate;
    private LocalDate tripArrivalDate;
    private String tripDestination;
    private Boolean isDomestic;
    private List<Itinerary> itineraries;
    private Integer likeCount;
    private List<Comment> comments;


}
