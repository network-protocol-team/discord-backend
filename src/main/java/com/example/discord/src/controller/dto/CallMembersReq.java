package com.example.discord.src.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CallMembersReq {
    private final String sender;
    private final String channelId;
}
