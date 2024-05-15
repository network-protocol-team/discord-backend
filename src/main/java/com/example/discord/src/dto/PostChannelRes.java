package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description="방 생성 response DTO")
public class PostChannelRes {
    @Schema(description = "채널 id")
    private Long channelId;

    @Schema(description = "채널명")
    private String channelName;
}
