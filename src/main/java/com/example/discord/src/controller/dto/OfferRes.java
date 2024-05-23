package com.example.discord.src.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class OfferRes {
    private final String sender;
    private final String channelId;
    private final String userKey;
    private final Object offer;
}
