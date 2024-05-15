package com.example.discord.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ApiResponseStatus {

    /**
        성공
     */

    SUCCESS(true, 200, "요청에 성공했습니다."),

    /**
     * 400 번대
     * 잘못된 요청 에러
     *
     */
    NOT_FOUND_ERROR(false, HttpStatus.NOT_FOUND.value(), "존재하지 않는 페이지입니다."),

    // 커스텀 에러 예시
    NOT_BYE_TOKKI_ERROR(false, HttpStatus.BAD_REQUEST.value(), "토끼는 떠나보낼 수 없습니다."),

    DUPLICATE_NICKNAME_ERROR(false, HttpStatus.NOT_FOUND.value(), "중복된 유저이름입니다."),

    DUPLICATE_CHANNELNAME_ERROR(false, HttpStatus.NOT_FOUND.value(), "중복된 채널명입니다."),

    /**
     * 500 번대
     * 서버에러
     *
     */

    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.")

    ;

    private final boolean isSuccess;
    private final int code;
    private final String message;


}
