package com.example.discord.src.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CallMembersRes {
    private final String sender;
    private final String channelId;
}
