package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.GetMessageDTO;
import com.example.discord.src.dto.PubMessageReq;
import com.example.discord.src.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/channels/{channelId}/text")
    @SendTo("/sub/channels/{channelId}/text")
    public BaseResponse<PubMessageReq> publishMessage(@DestinationVariable("channelId") UUID channelId,
                                                      @Payload PubMessageReq pubMessageReq) {
        try {

            UUID messageId = messageService.storeMessage(
                    pubMessageReq.getNickName(), channelId, pubMessageReq.getContent());

            GetMessageDTO getMessageDTO = messageService.findMessageById(messageId);

            return new BaseResponse(getMessageDTO);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
