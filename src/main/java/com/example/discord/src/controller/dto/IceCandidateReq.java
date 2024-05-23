package com.example.discord.src.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IceCandidateReq {
    private final String sender;
    private final String channelId;
    private final String userKey;
    private final Object iceCandidate;
}
