package com.travel.toy3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    INCORRECT_USERNAME_FORMAT("잘못된 ID 형식입니다. ID는 이메일 형식이어야 합니다."),
    INCORRECT_JSON_FORMAT("잘못된 요청 (JSON) 형식입니다."),
    INVALID_USERNAME("ID가 존재하지 않습니다."),
    INVALID_PASSWORD("잘못된 비밀번호입니다."),
    INVALID_ADDRESS("입력하신 주소의 정보가 존재하지 않습니다."),
    INVALID_TRIP("입력하신 여행 id에 해당하는 여행 정보가 존재하지 않습니다."),
    INVALID_ITINERARY("입력하신 여정 id에 해당하는 여정 정보가 존재하지 않습니다."),
    NO_EDIT_PERMISSION("수정 권한이 없습니다. 본인이 작성한 정보만 수정이 가능합니다."),
    INVALID_SEARCH_RESULT("입력하신 여행지에 해당하는 여행 정보가 존재하지 않습니다."),
    EMPTY_LIKE_LIST("아직 좋아요를 누르신 여행 정보가 없습니다.");

    private final String message;
}
