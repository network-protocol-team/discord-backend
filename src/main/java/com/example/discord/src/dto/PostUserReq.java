package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Schema(description="유저 등록 request DTO")
public class PostUserReq {
    @Schema(description = "유저 닉네임")
    private final String nickName;
}
