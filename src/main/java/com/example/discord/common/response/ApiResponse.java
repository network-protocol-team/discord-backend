package com.example.discord.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 "isSuccess" : true, // 성공 여부 (true/false)
 "code": 200,     // 응답 코드
 "message":"요청에 성공했습니다.", // 응답 메세지
 "result":{
 ....
 }
 */

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    @JsonProperty
    private final Boolean isSuccess;
    private final int code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우
    public ApiResponse(T result) {
        this.isSuccess = ApiResponseStatus.SUCCESS.isSuccess();
        this.message = ApiResponseStatus.SUCCESS.getMessage();
        this.code = ApiResponseStatus.SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public ApiResponse(ApiResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }
}
