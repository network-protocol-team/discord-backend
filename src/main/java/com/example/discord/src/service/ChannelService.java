package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.src.dto.PostChannelRes;
import com.example.discord.src.entity.Channel;
import com.example.discord.src.entity.Message;
import com.example.discord.src.repository.ChannelRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;

    /* 채널 생성 */
    @Transactional
    public PostChannelRes createChannel(String channelName) {
        validateDuplicateChannelName(channelName);

        Channel channel = new Channel(channelName);
        channelRepository.save(channel);

        PostChannelRes postChannelRes = PostChannelRes.builder()
                .channelId(channel.getChannelId())
                .channelName(channel.getChannelName())
                .build();
        log.info("channel " + channel.getChannelName() + " created");

        return postChannelRes;
    }

    /* 채널 삭제 */
    @Transactional
    public void deleteChannelById(UUID channelId) {
        validateChannelId(channelId);
        channelRepository.deleteById(channelId);
    }

    /* 전체 채널 조회 */
    @Transactional(readOnly = true)
    public List<Channel> listAllChannels() {
        List<Channel> channelList = channelRepository.findAll();
        return channelList;
    }

    /* 채널 채팅 내역 전체 조회 */
    @Transactional(readOnly = true)
    public List<Message> listChannelMessages(UUID channelId) {
        validateChannelId(channelId);

        List<Message> messageList = channelRepository.findById(channelId).get()
                .getMessageList();

        messageList.stream()
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .collect(Collectors.toList());

        return messageList;
    }

    /* 채널명 중복 체크 */
    @Transactional(readOnly = true)
    public void validateDuplicateChannelName(String channelName) {
        if(channelRepository.findByChannelName(channelName).isPresent())
            throw new BaseException(ApiResponseStatus.DUPLICATE_CHANNELNAME_ERROR);
    }

    /* 채널 id 유효 체크 */
    @Transactional(readOnly = true)
    public void validateChannelId(UUID channelId) {
        if(channelRepository.findById(channelId).isEmpty())
            throw new BaseException(ApiResponseStatus.NO_EXIST_CHANNEL_ERROR);
    }
}
