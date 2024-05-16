package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.GetChannelRes;
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
import org.springframework.web.bind.annotation.*;

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
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @Operation(summary = "채널 삭제", description = "채널id를 URL 경로로 받고 해당 채널을 삭제하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 채널입니다.")
    })
    @DeleteMapping()
    public BaseResponse<ApiResponseStatus> deleteChannel(@PathVariable Long channelId) {
        try {

            channelService.deleteChannelById(channelId);
            return new BaseResponse<>(ApiResponseStatus.SUCCESS);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @GetMapping()
    public BaseResponse<GetChannelRes> getChannels() {
        GetChannelRes getChannelRes = channelService.listAllChannels();
        return new BaseResponse<>(getChannelRes);
    }
}
