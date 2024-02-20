package com.travel.toy3.domain.member.dto;

import com.travel.toy3.domain.member.entity.Member;
import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private String username;
    private String password;
    private String uname;
    // 본인이 작성한 여행 정보 리스트
    private List<Trip> trips;

    public static MemberDTO fromEntity(Member member) {
        return MemberDTO.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .uname(member.getUname())
                .build();
    }
}
