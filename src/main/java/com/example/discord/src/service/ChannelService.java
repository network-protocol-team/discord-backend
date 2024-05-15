package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.src.dto.PostChannelReq;
import com.example.discord.src.dto.PostChannelRes;
import com.example.discord.src.entity.Channel;
import com.example.discord.src.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;

    /* 채널명 중복 체크 */
    private Boolean validateDuplicateChannelName(String channelName) {
        return channelRepository.findByChannelName(channelName).isPresent();
    }

    /* 채널 생성 */
    public PostChannelRes createChannel(PostChannelReq postChannelReq) {
        if(validateDuplicateChannelName(postChannelReq.getChannelName()))
            throw new BaseException(ApiResponseStatus.DUPLICATE_CHANNELNAME_ERROR);

        Channel channel = Channel.builder().channelName(postChannelReq.getChannelName()).build();
        channelRepository.save(channel);

        PostChannelRes postChannelRes = PostChannelRes.builder()
                .channelId(channel.getChannelId())
                .channelName(channel.getChannelName())
                .build();
        log.info("channel " + channel.getChannelName() + " created");

        return postChannelRes;
    }
}
