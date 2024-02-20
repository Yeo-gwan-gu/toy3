package com.travel.toy3.domain.member.dto;

import com.travel.toy3.domain.trip.entity.Trip;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemberDTO {
    private String username;
    private String password;
    private String uname;
    // 본인이 작성한 여행 정보 리스트
    private List<Trip> trips;
}
