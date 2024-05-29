package com.example.discord.src.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ManageReq {
    private final String sender;
    private final String channelId;
    private final String channelName;
    private final String status;
}
