package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.trip.dto.LikeDTO;
import com.travel.toy3.domain.trip.service.LikeService;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/api/like/{trip-id}")
    public LikeDTO.likeResponse addLike(
            @PathVariable("trip-id") Long tripId
    ) {
        if(tripId == null) {
            throw new CustomException(CustomErrorCode.INVALID_TRIP);
        }
        return likeService.addLike(tripId);
    }

    @PutMapping("/api/like/{trip-id}")
    public LikeDTO.likeResponse updateLike(
            @PathVariable("trip-id") Long tripId
    ) {
        return likeService.updateLike(tripId);
    }

    @GetMapping("/api/trips/like")
    public List<LikeDTO.likeResponse> listLikedTrips(
//            @PathVariable("trip-id") Long tripId
    ) {
//        return likeService.listLikedTrips(tripId);
        return likeService.listLikedTrips();
    }
}
