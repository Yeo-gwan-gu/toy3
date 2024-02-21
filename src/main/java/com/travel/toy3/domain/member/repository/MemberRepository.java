package com.travel.toy3.domain.member.repository;

import com.travel.toy3.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
