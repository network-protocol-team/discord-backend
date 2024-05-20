package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description="유저 등록 request DTO")
public class PostUserReq {
    @Schema(description = "유저 닉네임")
    private String nickName;
}
