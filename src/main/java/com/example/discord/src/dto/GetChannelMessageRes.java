package com.example.discord.src.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class GetChannelMessageRes {
    List<GetMessageRes> getMessageResList;
}
