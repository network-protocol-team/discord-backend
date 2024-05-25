package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Schema(description="채널 조회 response DTO")
public class GetChannelRes {
    @Schema(description = "채널 id")
    private UUID channelId;

    @Schema(description = "채널명")
    private String channelName;
}
