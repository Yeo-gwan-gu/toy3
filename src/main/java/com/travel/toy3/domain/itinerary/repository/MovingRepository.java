package com.travel.toy3.domain.itinerary.repository;

import com.travel.toy3.domain.itinerary.entity.Moving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovingRepository extends JpaRepository<Moving, Long> {
}
