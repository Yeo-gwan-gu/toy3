package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.service.MemberService;
import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public MemberRestController(
            MemberService memberService, UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        this.memberService = memberService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<Object>> login(
            @RequestBody MemberDTO memberDTO, HttpSession session
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getUsername());
        // 아이디 오입력
        if (userDetails.getUsername().isEmpty() || userDetails.getUsername().isBlank()) {
            var response = ApiResponse.builder()
                    .resultCode(INVALID_USERNAME.getCode())
                    .errorMessage(INVALID_USERNAME.getMessage())
                    .build();
            return ResponseEntity
                    .status(INVALID_USERNAME.getCode())
                    .body(response);
        }
        // 비밀번호 확인
        if (!passwordEncoder.matches(memberDTO.getPassword(), userDetails.getPassword())) {
            var response = ApiResponse.builder()
                    .resultCode(INVALID_PASSWORD.getCode())
                    .errorMessage(INVALID_PASSWORD.getMessage())
                    .build();
            return ResponseEntity
                    .status(INVALID_PASSWORD.getCode())
                    .body(response);
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

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Object>> checkLogin(HttpSession session) {
        // 세션에 저장된 인증 정보를 가져옴
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContext == null || securityContext.getAuthentication() == null) {
            var response = ApiResponse.builder()
                    .resultCode(NO_ACCESS_PERMISSION.getCode())
                    .errorMessage(NO_ACCESS_PERMISSION.getMessage())
                    .build();
            return ResponseEntity
                    .status(NO_ACCESS_PERMISSION.getCode())
                    .body(response);
        }
        Authentication authentication = securityContext.getAuthentication();
        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("현재 로그인된 사용자: " + authentication.getName())
                .build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/signout")
    public ResponseEntity<ApiResponse<Object>> logout(
            HttpServletRequest request, HttpSession session
    ) {
        // 세션에 저장된 인증 정보를 가져옴
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        // 로그아웃 상태로 로그아웃 시도 시 반환
        if (securityContext == null || securityContext.getAuthentication() == null) {
            var response = ApiResponse.builder()
                    .resultCode(UNACCEPTABLE_LOGOUT_REQUEST.getCode())
                    .errorMessage(UNACCEPTABLE_LOGOUT_REQUEST.getMessage())
                    .build();
            return ResponseEntity
                    .status(UNACCEPTABLE_LOGOUT_REQUEST.getCode())
                    .body(response);
        }
        log.info("로그아웃 성공");
        // 세션 무효화
        session.invalidate();
        // 보안 컨텍스트 지우기
        SecurityContextHolder.clearContext();
        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("로그아웃 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 회원 전체 조회
    @GetMapping
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers()
                .stream()
                .map(MemberDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<Object>> createMember(
            @Valid @RequestBody MemberDTO memberDTO,
            HttpSession session
    ) {
        // 세션에서 인증 정보 가져오기
        SecurityContext securityContext = (SecurityContext) session
                .getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        // 이미 로그인 상태에서 회원가입을 요청할 경우
        if (securityContext != null && securityContext.getAuthentication() != null) {
            var response = ApiResponse.builder()
                    .resultCode(UNACCEPTABLE_JOIN_REQUEST.getCode())
                    .errorMessage(UNACCEPTABLE_JOIN_REQUEST.getMessage())
                    .build();
            return ResponseEntity
                    .status(UNACCEPTABLE_JOIN_REQUEST.getCode())
                    .body(response);
        }
        // 이미 가입된 회원인지 확인
        try {
            UserDetails existingUser = userDetailsService.loadUserByUsername(memberDTO.getUsername());
            // 아이디 오입력
            if (existingUser.getUsername().isEmpty() || existingUser.getUsername().isBlank()) {
                var response = ApiResponse.builder()
                        .resultCode(INVALID_USERNAME.getCode())
                        .errorMessage(INVALID_USERNAME.getMessage())
                        .build();
                return ResponseEntity
                        .status(INVALID_USERNAME.getCode())
                        .body(response);
            }
            if (existingUser != null) {
                var response = ApiResponse.builder()
                        .resultCode(HttpStatus.BAD_REQUEST.value())
                        .errorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .data("이미 가입된 회원입니다.")
                        .build();
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST.value())
                        .body(response);
            }
        } catch (UsernameNotFoundException e) {
            // 사용자를 찾지 못하는 경우 회원가입 진행
            Member member = memberDTO.toEntity();
            Member savedMember = memberService.saveMember(member);
        }
        var response = ApiResponse.builder()
                .resultCode(HttpStatus.OK.value())
                .resultMessage(HttpStatus.OK.getReasonPhrase())
                .data("회원가입 성공")
                .build();
        return ResponseEntity.ok().body(response);
    }

}
