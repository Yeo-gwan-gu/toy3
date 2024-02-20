package com.travel.toy3.domain.trip.entity;

import com.travel.toy3.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId; // 좋아요 id

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "member_id")
//    private Member member; // 좋아요 누른 사용자

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trip_id")
    private Trip trip; // 여행 id

}