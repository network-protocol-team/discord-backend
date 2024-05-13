package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.Cookie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description="유저 등록 response DTO")
public class PostUserRes {
    @Schema(description = "유저 ID")
    private Long userId;

    @Schema(description = "쿠키")
    private Cookie cookie;
}
