package com.travel.toy3.domain.itinerary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 숙박 id (PK)

    @ManyToOne
    @JoinColumn(name = "itineraryId")
    private Itinerary itinerary; // 여정 테이블과 관계 설정 -> 여정 id (FK)

    private String accommodationName; // 숙소명
    private Double accommodationPlaceLatitude; // 숙소 위도
    private Double accommodationPlaceLongitude; // 숙소 경도
    private LocalDateTime departureDatetime; // 출발 일시
    private LocalDateTime arrivalDatetime; // 도착 일시

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
