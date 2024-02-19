package com.travel.toy3.domain.trip.entity;

import com.travel.toy3.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long tripId; // 여행 id

    @ManyToOne(fetch = FetchType.LAZY) // 1:N 인 경우에 사용
    @JoinColumn(name = "member_id")
    private Member member; // 사용자 id

    @Column(name = "trip_name")
    private String tripName; // 여행 이름

    @Column(name = "trip_departure_date")
    private LocalDate tripDepartureDate; // 여행 출발 날짜

    @Column(name = "trip_arrival_date")
    private LocalDate tripArrivalDate; // 여행 도착 날짜

    @Column(name = "trip_destination")
    private String tripDestination; // 여행지

    @Column(name = "is_domestic")
    private boolean isDomestic; // 국내외 여부

    @Column(name = "like_count")
    private int likeCount; // 좋아요 개수

}