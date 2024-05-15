package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.PostChannelReq;
import com.example.discord.src.dto.PostChannelRes;
import com.example.discord.src.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels")
@Tag(name = "channels", description = "채널 생성 api")
@RequiredArgsConstructor
@Slf4j
public class ChannelController {
    private final ChannelService channelService;

    @Operation(summary = "채널 생성", description = "채널명을 받아 방을 생성하고 채널id와 채널명을 반환하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "중복된 채널명입니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json"))
    })
    @PostMapping()
    public BaseResponse<PostChannelRes> postChannel(@RequestBody PostChannelReq postChannelReq) {
        try {

            PostChannelRes postChannelRes = channelService.createChannel(postChannelReq);
            return new BaseResponse<>(postChannelRes);

        }catch(BaseException baseException) {
            if(baseException.getStatus().equals(ApiResponseStatus.DUPLICATE_CHANNELNAME_ERROR))
                return new BaseResponse<>(ApiResponseStatus.DUPLICATE_CHANNELNAME_ERROR);
            else {
                log.debug(baseException.getMessage());
                throw baseException;
            }
        }
    }
}
