package com.example.discord.src.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description="채널 생성 request DTO")
public class PostChannelReq {
    @Schema(description = "채널명")
    private String channelName;
}
