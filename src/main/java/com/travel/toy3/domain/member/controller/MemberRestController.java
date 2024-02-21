package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/members")
public class MemberRestController {
    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers()
                .stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
