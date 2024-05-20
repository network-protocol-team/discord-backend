package com.example.discord.service;

import com.example.discord.src.dto.GetMessageRes;
import com.example.discord.src.repository.ChannelRepository;
import com.example.discord.src.repository.MessageRepository;
import com.example.discord.src.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class MessageServiceIntegrationTest {
    @Autowired MessageService messageService;
    @Autowired ChannelRepository channelRepository;

    @Test
    @Transactional
    public void storeMessage() {
        String nickName = "test";
        Long channelId = 2L;
        String content = "Hello";

        GetMessageRes getMessageRes = messageService.storeMessage(nickName, channelId, content);

        log.info("nickName: {}", getMessageRes.getNickName());
        log.info("content: {}", getMessageRes.getContent());
        log.info("createdAt: {}", getMessageRes.getCreatedAt());

        Assertions.assertEquals(true,
                channelRepository.findById(channelId).get().getMessageList().stream()
                .filter(m -> m.getUser().getNickName().equals(nickName))
                .filter(m -> m.getChannel().getChannelId().equals(channelId))
                .findAny().isPresent()
        );
    }
}
