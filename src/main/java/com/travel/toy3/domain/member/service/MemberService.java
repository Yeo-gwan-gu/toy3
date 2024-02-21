package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

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
