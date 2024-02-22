package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.entity.Role;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.travel.toy3.exception.CustomErrorCode.*;
import static com.travel.toy3.exception.CustomErrorCode.BAD_REQUEST;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;
    public MemberService(MemberRepository memberRepository,
                         RoleService roleService,
                         BCryptPasswordEncoder passwordEncoder,
                         UserDetailsService userDetailsService
    ) {
        this.memberRepository = memberRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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

    public Authentication getAuthentication(HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

        if (securityContext == null || securityContext.getAuthentication() == null) {
            throw new CustomException(NO_ACCESS_PERMISSION);
        }
        return securityContext.getAuthentication();
    }

    public void signOut(HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null || securityContext.getAuthentication() == null) {
            throw new CustomException(UNACCEPTABLE_LOGOUT_REQUEST);
        }
        log.info("로그아웃 성공");
        session.invalidate();
        SecurityContextHolder.clearContext();
    }

    public void createMember(MemberDTO memberDTO, HttpSession session) {
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext != null && securityContext.getAuthentication() != null) {
            throw new CustomException(UNACCEPTABLE_JOIN_REQUEST);
        }
        UserDetails existingUser = null;
        try {
            existingUser = userDetailsService.loadUserByUsername(memberDTO.getUsername());
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾지 못하는 경우 회원가입 진행
            Member member = memberDTO.toEntity();
            Member savedMember = saveMember(member);
        }
        if (existingUser != null) {
            throw new CustomException(BAD_REQUEST);
        }
    }
}
