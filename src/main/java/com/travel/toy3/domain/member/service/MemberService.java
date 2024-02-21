package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.domain.member.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        Role userRole = roleRepository.findByName("USER");
        member.addRole(userRole);
        return memberRepository.save(member);
    }
}
