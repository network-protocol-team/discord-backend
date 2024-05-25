package com.example.discord.service;

import com.example.discord.src.dto.GetMessageDTO;
import com.example.discord.src.repository.ChannelRepository;
import com.example.discord.src.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@SpringBootTest
public class MessageServiceIntegrationTest {
    @Autowired MessageService messageService;
    @Autowired ChannelRepository channelRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void storeMessage() {
        String nickName = "user2";
        UUID channelId = UUID.fromString("646bd3c3-f654-49d8-a084-6e7fc558de9e");
        String content = "bye";

        UUID messageId = messageService.storeMessage(nickName, channelId, content);
        GetMessageDTO getMessageDTO = messageService.findMessageById(messageId);

        log.info("nickName: {}", getMessageDTO.getNickName());
        log.info("content: {}", getMessageDTO.getContent());
        log.info("createdAt: {}", getMessageDTO.getCreatedAt());

        Assertions.assertEquals(true,
                channelRepository.findById(channelId).get().getMessageList().stream()
                .filter(m -> m.getUser().getNickName().equals(nickName))
                .filter(m -> m.getChannel().getChannelId().equals(channelId))
                .findAny().isPresent()
        );
    }
}
