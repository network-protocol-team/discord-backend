package com.example.discord.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "base response format")
public class BaseResponse<T> {
    @JsonProperty
    @Schema(description = "요청처리 성공여부")
    private final Boolean isSuccess;

    @Schema(description = "상태코드")
    private final int code;

    @Schema(description = "상태메시지")
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "요청 처리 결과값")
    private T result;

    // 요청에 성공한 경우
    public BaseResponse(T result) {
        this.isSuccess = ApiResponseStatus.SUCCESS.isSuccess();
        this.message = ApiResponseStatus.SUCCESS.getMessage();
        this.code = ApiResponseStatus.SUCCESS.getCode();
        this.result = result;
    }

    // 요청에 실패한 경우
    public BaseResponse(ApiResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

}
