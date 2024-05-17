package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.src.dto.GetChannelRes;
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

    /* 채널 삭제 */
    public void deleteChannelById(Long channelId) {
        if(!validateChannelId(channelId))
            throw new BaseException(ApiResponseStatus.NO_EXIST_CHANNEL_ERROR);

        channelRepository.deleteById(channelId);
    }

    /* 전체 채널 조회 */
    public GetChannelRes listAllChannels() {
        GetChannelRes getChannelRes = new GetChannelRes(channelRepository.findAll());
        return getChannelRes;
    }

    /* 채널명 중복 체크 */
    private Boolean validateDuplicateChannelName(String channelName) {
        return channelRepository.findByChannelName(channelName).isPresent();
    }

    /* 채널 id 유효 체크 */
    private Boolean validateChannelId(Long channelId) {
        return channelRepository.findById(channelId).isPresent();
    }
}
