package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.service.MemberService;
import com.travel.toy3.domain.trip.dto.LikeDTO;
import com.travel.toy3.domain.trip.service.LikeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/like/{trip-id}")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MemberService memberService;

//    @PostMapping
//    public ResponseEntity<Object> addLike(@PathVariable("trip-id") @Positive Long boardId,
//                                          @AuthenticationPrincipal String username ) {
//
//        Member member = memberService.findByUsername(username);
//        likeService.addLike(boardId,member);
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//    }

    @PostMapping
    public LikeDTO.likeResponse addLike(
            @RequestBody final LikeDTO.likeRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        likeService.addLike(request);
        return null;
    }

    @PutMapping
    public LikeDTO.likeResponse updateLike(
            @PathVariable("trip-id") Long tripId,
            @RequestBody final LikeDTO.likeRequest request
    ){
         likeService.updateLike(tripId, request);
        return likeService.updateLike(tripId, request);
    }

}
