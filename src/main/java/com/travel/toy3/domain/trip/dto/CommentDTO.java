package com.travel.toy3.domain.trip.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.entity.Trip;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

public class CommentDTO {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class commentRequest{
        @NotNull
        private Long commentId;
        @NonNull
        private Member memberId;
        @NotNull
        private Trip tripId;
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class commentResponse{
        private Long commentId;
        private Member memberId;
        private Trip tripId;
        private String content;

//        public static commentResponse(
//                @NotNull Member member, @NotNull Trip trip, Comment comment){
//            return commentResponse.builder()
//                    .commentId(comment.getCommentId())
//                    .memberId(member.getmemberId())
//                    .tripId(trip.getTripId())
//                    .content(comment.getContent())
//                    .builder();
//        }
    }
}
