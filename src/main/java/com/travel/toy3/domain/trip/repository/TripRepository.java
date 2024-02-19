package com.travel.toy3.domain.trip.repository;

import com.travel.toy3.domain.trip.dto.TripDTO;
import com.travel.toy3.domain.trip.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {

    ResponseEntity<TripDTO> creteTrip(TripDTO trip);

}