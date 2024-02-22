package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.entity.Like;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TripDTO {

    private Long id;
    private String username;
    private String tripName;
    private LocalDate tripDepartureDate;
    private LocalDate tripArrivalDate;
    private String tripDestination;
    private Boolean isDomestic;
    private Integer likeCount;
    private Integer commentCount;

    public static TripDTO fromEntity(
            @NotNull Trip trip
           //   @NotNull Trip trip, Member member, Like like, Comment comment
    ) {
        return TripDTO.builder()
                .id(trip.getId())
//                .member(member.getid())
                .tripName(trip.getTripName())
                .tripDepartureDate(trip.getTripDepartureDate())
                .tripArrivalDate(trip.getTripArrivalDate())
                .tripDestination(trip.getTripDestination())
                .isDomestic(trip.getIsDomestic())
//                .likeCount(like.getLikeCount())
//                .commentCount(comment.getCommentCount())
                .build();
    }

}
