package com.travel.toy3.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Moving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 이동 id (PK)

    @ManyToOne
    @JoinColumn(name = "itineraryId")
    private Itinerary itinerary; // 여정 테이블과 관계 설정 -> 여정 id (FK)

    private String vehicle; // 이동 수단
    private String departurePlace; // 출발지
    private String destinationPlace; // 도착지
    private Double departurePlaceLatitude; // 출발지 위도
    private Double departurePlaceLongitude; // 출발지 경도
    private Double destinationPlaceLatitude; // 도착지 위도
    private Double destinationPlaceLongitude; // 도착지 경도
    private LocalDateTime departureDatetime; // 출발 일시
    private LocalDateTime arrivalDatetime; // 도착 일시

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
