package com.travel.toy3.domain.member.controller;

import com.travel.toy3.domain.member.dto.MemberDTO;
import com.travel.toy3.exception.CustomErrorCode;
import com.travel.toy3.exception.CustomException;
import com.travel.toy3.exception.CustomExceptionHandler;
import com.travel.toy3.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public LoginController(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("/signin")
//    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getUsername());
//        // 비밀번호 확인
//        if (!passwordEncoder.matches(memberDTO.getPassword(), userDetails.getPassword())) {
//            return new ResponseEntity<>("비밀번호가 틀렸습니다.", HttpStatus.UNAUTHORIZED);
//        }
//        // 인증 객체 생성
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        // 인증 정보 저장
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
//        log.info("로그인 성공");
//        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
//    }

    @PostMapping("/signin")
    public ApiResponse<Object> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberDTO.getUsername());
        // 비밀번호 확인
        if (!passwordEncoder.matches(memberDTO.getPassword(), userDetails.getPassword())) {
            return ApiResponse.builder()
                    .data(CustomErrorCode.INVALID_PASSWORD)
                    .resultCode(HttpStatus.UNAUTHORIZED.value())
                    .errorMessage("잘못된 비밀번호입니다.")
                    .build();
        }
        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // 인증 정보 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        log.info("로그인 성공");
        return ApiResponse.builder()
                .data("username : "+memberDTO.getUsername())
                .resultCode(HttpStatus.OK.value())
                .resultMessage("로그인 성공")
                .build();
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkLogin(HttpSession session) {
        // 세션에 저장된 인증 정보를 가져옴
        SecurityContext securityContext = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            log.info("현재 로그인된 사용자 : {}", authentication.getName());
            return new ResponseEntity<>("현재 로그인된 사용자: " + authentication.getName(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 상태가 아닙니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpSession session) {
        log.info("로그아웃 성공");
        // 세션 무효화
        session.invalidate();
        // 보안 컨텍스트 지우기
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
    }
}