package com.example.discord.service;

import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.controller.ChannelController;
import com.example.discord.src.dto.GetChannelRes;
import com.example.discord.src.dto.PostChannelReq;
import com.example.discord.src.dto.PostChannelRes;
import com.example.discord.src.repository.ChannelRepository;
import com.example.discord.src.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class ChannelServiceIntegrationTest {
    @Autowired ChannelController channelController;
    @Autowired ChannelService channelService;
    @Autowired ChannelRepository channelRepository;

    @Test
    public void postChannel() {
        PostChannelReq postChannelReq = new PostChannelReq("채널3");
        BaseResponse<PostChannelRes> responseBody = channelController.postChannel(postChannelReq);

        log.info("success: {}", responseBody.getIsSuccess());
        log.info("code: {}", responseBody.getCode());
        log.info("message: {}", responseBody.getMessage());

        if(responseBody.getIsSuccess()) {
            PostChannelRes postChannelRes = responseBody.getResult();

            log.info("result: ");
            log.info("channelId: {}", postChannelRes.getChannelId());
            log.info("channelName: {}", postChannelRes.getChannelName());

            Assertions.assertEquals(postChannelReq.getChannelName(), postChannelRes.getChannelName());
        }
        else {
            Assertions.assertEquals(true, channelRepository.
                    findByChannelName(postChannelReq.getChannelName())
                    .isPresent());
        }
    }

    @Test
    public void deleteChannel() {
        Long channelId = 1L;
        BaseResponse<ApiResponseStatus> responseBody = channelController.deleteChannel(channelId);

        log.info("success: {}", responseBody.getIsSuccess());
        log.info("code: {}", responseBody.getCode());
        log.info("message: {}", responseBody.getMessage());

        Assertions.assertEquals(false, channelRepository.existsById(channelId));
    }

    @Test
    public void getChannels() {
        BaseResponse<GetChannelRes> responseBody = channelController.getChannels();

        log.info("success: {}", responseBody.getIsSuccess());
        log.info("code: {}", responseBody.getCode());
        log.info("message: {}", responseBody.getMessage());

        responseBody.getResult().getGetChannelResList()
                .stream()
                .map(c -> c.getChannelName())
                .forEach(channelName -> log.info("channelName: {}", channelName));
    }
}
