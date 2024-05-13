package com.example.discord.src.controller;

import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.service.ExampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.discord.src.dto.ExampleRes;

@RestController
@RequestMapping("/examples")
@Tag(name = "example", description = "여러 인사하기 api")        // 해당 컨트롤러에 대한 swagger 이름과 설명 등을 설정한다
@RequiredArgsConstructor
@Slf4j
public class ExampleController {

    private final ExampleService exampleService;

    @GetMapping("/hello")
    @Operation(summary = "인사하기", description = "대상을 지정해서 인사말을 반환하는 api")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json"))
    public BaseResponse<ExampleRes> getHello(){
        ExampleRes exampleRes = exampleService.getHello("gosumdochi");
        return new BaseResponse<>(ApiResponseStatus.SUCCESS, exampleRes);
    }

    @GetMapping("/bye")
    @Operation(summary = "작별인사하기", description = "대상을 지정해서 작별인사말을 반환하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "토끼는 떠나보낼 수 없습니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json")),
    })
    public BaseResponse<ExampleRes> getBye(){
        ExampleRes exampleRes = exampleService.getBye("tokki");
        return new BaseResponse<>(ApiResponseStatus.SUCCESS, exampleRes);
    }
}
