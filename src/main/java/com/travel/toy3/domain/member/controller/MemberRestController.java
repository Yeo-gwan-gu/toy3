package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberRestController {
    @Autowired
    private MemberService memberService;

    // 회원 전체 조회
    @GetMapping
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers()
                .stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<MemberDTO> createMember(@RequestBody MemberDTO memberDTO) {
        Member member = memberDTO.toEntity();
        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(MemberDTO.fromEntity(savedMember), HttpStatus.CREATED);
    }

}
