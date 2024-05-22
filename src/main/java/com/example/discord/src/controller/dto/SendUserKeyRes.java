package com.example.discord.src.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class SendUserKeyRes {
    private final String sender;
    private final String channelId;
    private final List<String> userKeys;
}
