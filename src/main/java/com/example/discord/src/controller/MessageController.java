package com.example.discord.src.controller;

import com.example.discord.src.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/pub/channels/{id}/text")
    public void publishMessage(Message message) throws Exception {

        String subUrl = "/sub/channels/" + message.getChannelId() + "/text";
        simpMessageSendingOperations.convertAndSend(subUrl, message);
    }
}
