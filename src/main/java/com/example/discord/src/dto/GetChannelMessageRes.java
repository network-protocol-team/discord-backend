package com.example.discord.src.dto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetChannelMessageRes {
    List<GetMessageRes> messages;
}