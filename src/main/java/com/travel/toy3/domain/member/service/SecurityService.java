package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.exception.CustomException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Slf4j
@Service
public class SecurityService {

    private final UserDetailsService userDetailsService;
    private final MemberService memberService;

    public SecurityService(UserDetailsService userDetailsService, MemberService memberService) {
        this.userDetailsService = userDetailsService;
        this.memberService = memberService;
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
            Member savedMember = memberService.saveMember(member);
        }
        if (existingUser != null) {
            throw new CustomException(BAD_REQUEST);
        }
    }

}
