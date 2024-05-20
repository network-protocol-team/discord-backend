package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.GetMessageRes;
import com.example.discord.src.dto.PubMessageReq;
import com.example.discord.src.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;

    @MessageMapping("/pub/channels/{channelId}/text")
    @SendTo("/sub/channels/{channelId}/text")
    public BaseResponse<PubMessageReq> publishMessage(@DestinationVariable Long channelId,
                                                      @Payload PubMessageReq pubMessageReq) {
        try {

            GetMessageRes getMessageRes = messageService.storeMessage(
                    pubMessageReq.getNickName(), channelId, pubMessageReq.getContent());

            return new BaseResponse(getMessageRes);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
