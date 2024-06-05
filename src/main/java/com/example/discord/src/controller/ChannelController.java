package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.*;
import com.example.discord.src.entity.Message;
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

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channels")
@Tag(name = "channels", description = "채널 api")
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

            PostChannelRes postChannelRes = channelService.createChannel(postChannelReq.getChannelName());
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
    @DeleteMapping("/{channelId}")
    public BaseResponse<ApiResponseStatus> deleteChannel(@PathVariable("channelId")
                                                 @Schema(description="채널 id", example = "a5263a4a-b036-49b1-9dd3-58dcbf3207b8")
                                                                 UUID channelId) {
        try {

            channelService.deleteChannelById(channelId);
            return new BaseResponse<>(ApiResponseStatus.SUCCESS);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @Operation(summary = "모든 채널 조회", description = "api 요청 시 db에 저장된 모든 채널을 조회하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
    })
    @GetMapping()
    public BaseResponse<GetChannelRes> getChannels() {

        List<GetChannelDTO> channels = new ArrayList<>();

        channelService.listAllChannels().stream()
                .forEach(c -> {
                    GetChannelDTO channelDTO = GetChannelDTO.builder()
                            .channelId(c.getChannelId())
                            .channelName(c.getChannelName())
                            .build();
                    channels.add(channelDTO);
                });

        GetChannelRes getChannelRes = GetChannelRes.builder()
                .channelList(channels)
                .build();

        return new BaseResponse<>(getChannelRes);
    }

    @Operation(summary = "채널 채팅 내역 조회", description = "채널 id를 받아 해당 채널 채팅 내역을 조회하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
    })
    @GetMapping("/{channelId}")
    public BaseResponse<GetChannelMessageRes> getChannelMessages(@PathVariable("channelId")
                                                                     @Schema(description="채널 id", example = "a5263a4a-b036-49b1-9dd3-58dcbf3207b8")
                                                                             UUID channelId) {
        try {

            List<GetMessageDTO> messages = new ArrayList<>();

            channelService.listChannelMessages(channelId).stream()
                    .forEach(m -> {
                        GetMessageDTO getMessageDTO = GetMessageDTO.builder()
                                .nickName(m.getUser().getNickName())
                                .createdAt(m.getCreatedAt())
                                .content(m.getContent())
                                .build();
                        messages.add(getMessageDTO);
                    });

            Collections.sort(messages, Comparator.comparing(GetMessageDTO::getCreatedAt));

            GetChannelMessageRes getChannelMessageRes = GetChannelMessageRes.builder()
                    .messageList(messages)
                    .build();

            return new BaseResponse(getChannelMessageRes);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    @Operation(summary = "채널명 업데이트", description = "채널명 업데이트 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 채널입니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json"))
    })
    @PatchMapping("/{channelId}")
    public BaseResponse<PostChannelRes> patchChannel(@PathVariable("channelId")
                                                     @Schema(description="채널 id", example = "a5263a4a-b036-49b1-9dd3-58dcbf3207b8")
                                                             UUID channelId, @RequestBody PostChannelReq postChannelReq) {
        try {
            PostChannelRes postChannelRes =
                    channelService.updateChannelName(channelId, postChannelReq.getChannelName());

            return new BaseResponse(postChannelRes);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }

    }
}
