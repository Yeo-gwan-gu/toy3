package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.trip.entity.Like;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LikeDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class likeRequest {
        @NotNull
        private Long memberId;
        private Long tripId;
        private String likeCount;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class likeResponse{
        @NotNull
        private Long memberId;
        @NotNull
        private Integer tripId;
        private String likeStatus;

        public static likeResponse fromLikeEntity(
                Like like){
            return likeResponse.builder()
                    .likeStatus(like.getStatus())
                    .build();
        }
    }

}
