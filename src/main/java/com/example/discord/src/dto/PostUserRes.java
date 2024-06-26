package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Schema(description="유저 등록 response DTO")
public class PostUserRes {
    @Schema(description = "유저 id")
    private UUID userId;

    @Schema(description = "유저 닉네임")
    private String nickName;
}
