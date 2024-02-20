package com.travel.toy3.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrorCode {
    // 잘못된 형식
    INCORRECT_USERNAME_FORMAT("잘못된 ID 형식입니다. ID는 이메일 형식이어야 합니다."),
    INCORRECT_JSON_FORMAT("잘못된 요청 (JSON) 형식입니다."),

    // 유효성 검증 실패
    INVALID_ADDRESS("입력하신 주소의 정보가 존재하지 않습니다."),
    INVALID_TRIP("입력하신 여행 id에 해당하는 여행 정보가 존재하지 않습니다."),
    INVALID_ITINERARY("입력하신 여정 id에 해당하는 여정 정보가 존재하지 않습니다."),
    INVALID_SEARCH_RESULT("입력하신 여행지에 해당하는 여행 정보가 존재하지 않습니다."),

    // 인증 실패
    INVALID_USERNAME("ID가 존재하지 않습니다."),
    INVALID_PASSWORD("잘못된 비밀번호입니다."),

    // 인증 관련 잘못된 요청
    UNACCEPTABLE_LOGIN_REQUEST("로그인 상태에서는 로그인을 할 수 없습니다."),
    UNACCEPTABLE_JOIN_REQUEST("로그인 상태에서는 회원가입을 할 수 없습니다."),
    UNACCEPTABLE_LOGOUT_REQUEST("로그아웃 상태에서는 로그아웃을 할 수 없습니다."),

    // 권한 없음
    NO_ACCESS_PERMISSION("접근 권한이 없습니다. 로그인이 필요합니다."),
    NO_EDIT_PERMISSION("수정 권한이 없습니다. 본인이 작성한 정보만 수정이 가능합니다."),

    // 안내가 필요한 경우
    EMPTY_LIKE_LIST("아직 좋아요를 누르신 여행 정보가 없습니다."),

    // 우리가 정의한 예외가 아닌 경우
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),
    BAD_REQUEST("잘못된 요청입니다.");

    private final String message;
}
