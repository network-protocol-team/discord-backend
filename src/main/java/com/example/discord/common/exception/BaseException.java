package com.example.discord.common.exception;

import com.example.discord.common.response.ApiResponseStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException{
    private ApiResponseStatus status;
    public BaseException(ApiResponseStatus status) {
        super(status.getMessage());
        this.status = status;
    }
}
