package com.travel.toy3.domain.member.service;

import com.travel.toy3.domain.member.dto.CustomMember;
import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.member.repository.MemberRepository;
import com.travel.toy3.exception.CustomException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

import static com.travel.toy3.exception.CustomErrorCode.INVALID_USERNAME;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optional = memberRepository.findByUsername(username);
        if (!optional.isPresent()) {
            throw  new CustomException(INVALID_USERNAME);
        }
        Member member=optional.get();
        return new CustomMember(member);
    }
}
