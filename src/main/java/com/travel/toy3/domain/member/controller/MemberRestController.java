package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.service.MemberService;
import com.travel.toy3.domain.member.service.SecurityService;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.travel.toy3.exception.CustomErrorCode.*;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberRestController {
    private final MemberService memberService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;

    public MemberRestController(
            MemberService memberService, UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder, SecurityService securityService
    ) {
        this.memberService = memberService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Object>> login(
            @RequestBody MemberDTO memberDTO, HttpSession session
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getUsername());
        // 비밀번호 확인 (Service 로 어떻게 빼야할지 몰라서 일단 여기서 처리)
        if (!passwordEncoder.matches(memberDTO.getPassword(), userDetails.getPassword())) {
            throw new CustomException(INVALID_PASSWORD);
        }
        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 인증 정보 저장
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder
                        .getContext());
        log.info("로그인 성공");
        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("로그인 성공")
                .build();

        return ResponseEntity.ok().body(response);
    }

    // 로그인 상태 확인
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Object>> checkLogin(HttpSession session) {
        Authentication authentication = securityService.getAuthentication(session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("현재 로그인된 사용자: " + authentication.getName())
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 로그아웃
    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Object>> logout(HttpSession session) {
        securityService.signOut(session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("로그아웃 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 회원 전체 조회
    @GetMapping("/admin")
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers()
                .stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Object>> createMember(
            @Valid @RequestBody MemberDTO memberDTO,
            HttpSession session
    ) {
        securityService.createMember(memberDTO, session);

        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("회원가입 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

}
