package com.example.discord.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.src.dto.PostChannelRes;
import com.example.discord.src.entity.Channel;
import com.example.discord.src.repository.ChannelRepository;
import com.example.discord.src.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ChannelServiceIntegrationTest {
    @Autowired ChannelService channelService;
    @Autowired ChannelRepository channelRepository;

    @Test
    public void createChannel() {
        String channelName = "채널2";

        try {

            PostChannelRes postChannelRes = channelService.createChannel(channelName);

            log.info("channelId: {}", postChannelRes.getChannelId());
            log.info("channelName: {}", postChannelRes.getChannelName());

            Assertions.assertEquals(channelName,
                    postChannelRes.getChannelName());

        }catch(BaseException baseException) {
            Assertions.assertEquals(true, channelRepository.
                    findByChannelName(channelName)
                    .isPresent());
        }
    }

    @Test
    public void deleteChannel() {
        UUID channelId = UUID.fromString("2cb60561-b997-4f78-8e20-342a3678d9b4");
        try {
            channelService.deleteChannelById(channelId);

            Assertions.assertEquals(true, channelRepository
                    .findById(channelId)
                    .isEmpty());

        }catch(BaseException baseException) {
            Assertions.assertEquals(true, channelRepository
                    .findById(channelId)
                    .isEmpty());
        }
    }

    @Test
    public void listAllChannels() {
        List<Channel> channelList = channelService.listAllChannels();

        channelList.stream()
                .map(c -> c.getChannelName())
                .forEach(channelName -> log.info("channelName: {}", channelName));
    }

    @Test
    @Transactional
    public void listChannelMessages() {
        UUID channelId = UUID.fromString("646bd3c3-f654-49d8-a084-6e7fc558de9e");

        channelService.listChannelMessages(channelId).stream()
                .forEach(m -> {
                    log.info("nickName: {}", m.getUser().getNickName());
                    log.info("content: {}", m.getContent());
                    log.info("createdAt: {}", m.getCreatedAt());
                });
    }
}
