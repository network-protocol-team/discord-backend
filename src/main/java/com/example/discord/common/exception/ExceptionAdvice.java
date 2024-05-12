package com.example.discord.common.exception;


import com.example.discord.common.response.BaseResponse;
import com.example.discord.common.response.ApiResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    /**
     * 직접 던진 예외(BaseException)의 경우
     */
    @ExceptionHandler(BaseException.class)
    public BaseResponse<ApiResponseStatus> baseExceptionHandle(BaseException exception) {
        log.warn("BaseException. error message: {}", exception.getMessage());
        return new BaseResponse<>(exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<ApiResponseStatus> unexcpectedExceptionHandle(Exception exception) {
        log.error("Exception has occured. ", exception);
        return new BaseResponse<>(ApiResponseStatus.UNEXPECTED_ERROR);
    }
}
