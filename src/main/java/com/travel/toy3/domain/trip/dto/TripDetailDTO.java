package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.itinerary.entity.Itinerary;
import com.travel.toy3.domain.trip.entity.Comment;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TripDetailDTO {

    private Long id;
    private String username;
    private String tripName;
    private LocalDate tripDepartureDate;
    private LocalDate tripArrivalDate;
    private String tripDestination;
    private Boolean isDomestic;
    private List<Itinerary> itineraries;
   // @Formula("(select count * from )")
    private Integer likeCount;
    private Integer commentCount;
    private List<CommentDTO.Response> comments;

    public static TripDetailDTO fromEntity(
            @NotNull Trip trip,
            @NotNull List<Comment> comments
           // @NotNull Trip trip, Member member, Itinerary itinerary, Like like, Comment comment
    ) {
        List<CommentDTO.Response> commentList = comments.stream()
                .map(CommentDTO.Response::fromEntity)
                .collect(Collectors.toList());

        return TripDetailDTO.builder()
                .id(trip.getId())
//                .username(member.getUsername())
                .tripName(trip.getTripName())
                .tripDepartureDate(trip.getTripDepartureDate())
                .tripArrivalDate(trip.getTripArrivalDate())
                .tripDestination(trip.getTripDestination())
                .isDomestic(trip.getIsDomestic())
//                .itineraries(itinerary.getItinerary())
//                .likeCount(like.getLikeCount())
//                .commentCount(comment.getCommentCount())
                .comments(commentList)
                .build();
    }
}
