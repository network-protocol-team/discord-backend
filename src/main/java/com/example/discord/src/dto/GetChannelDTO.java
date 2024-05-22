package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@Schema(description="채널 조회 response DTO")
public class GetChannelDTO {
    @Schema(description = "채널 id")
    private Long channelId;

    @Schema(description = "채널명")
    private String channelName;
}
