package com.travel.toy3.domain.itinerary.repository;

import com.travel.toy3.domain.itinerary.entity.Moving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovingRepository extends JpaRepository<Moving, Long> {
    Moving findByItinerary_Id(Long itineraryId);
}
