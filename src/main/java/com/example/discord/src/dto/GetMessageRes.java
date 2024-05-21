package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description="채널 채팅 내역 조회 response DTO")
public class GetMessageRes {
    @Schema(description = "유저 닉네임")
    String nickName;

    @Schema(description = "채팅 메시지")
    String content;

    @Schema(description = "메시지 생성 시간")
    LocalDateTime createdAt;
}
