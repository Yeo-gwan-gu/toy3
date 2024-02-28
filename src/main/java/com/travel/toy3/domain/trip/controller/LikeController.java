package com.travel.toy3.domain.trip.controller;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.service.MemberService;
import com.travel.toy3.domain.trip.service.LikeService;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private MemberService memberService;

    @PostMapping("api/like/{trip-id}")
    public ResponseEntity addLike(@PathVariable("trip-id")@Positive Long boardId,
                                  @AuthenticationPrincipal String username ) {
        //이메일을 불러옴
        Member member = memberService.findByUsername(username);
        //id 랑 멤버 추가해 버림
        likeService.addLike(boardId,member);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
