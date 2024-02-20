package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.entity.Like;
import com.travel.toy3.domain.trip.entity.Trip;
import lombok.*;

public class LikeDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class likeRequest {
        @NonNull
        private Long memberId;
        @NonNull
        private Integer tripId;
        private Integer likeCount;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class likeResponse{
        @NonNull
        private Long memberId;
        @NonNull
        private Integer tripId;
        private Integer likeCount;

        public static likeResponse fromLikeEntity(@NonNull Member member, @NonNull Trip trip, Like like){
            return likeResponse.builder()
                    .memberId(member.getMemberId())
                    .tripId(trip.getTripId())
                    .likeCount(like.getLikeId())
                    .build();
        }
    }

}
