package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@Schema(description="채널 생성 response DTO")
public class PostChannelRes {
    @Schema(description = "채널 id")
    private UUID channelId;

    @Schema(description = "채널명")
    private String channelName;
}
