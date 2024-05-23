package com.example.discord.src.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendUserKeyReq {
    private final String sender;
    private final String channelId;
    private final String userKey;
    private final String state;
}
