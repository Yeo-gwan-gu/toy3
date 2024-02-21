package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        String hashedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(hashedPassword);
        Role userRole = roleService.findByName("USER");
        member.addRole(userRole);
        return memberRepository.save(member);
    }
}
