package com.example.discord.src.dto;

import com.example.discord.src.entity.Channel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetChannelRes {
    private final List<Channel> getChannelResList;
}
