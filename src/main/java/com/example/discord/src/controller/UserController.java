package com.example.discord.src.controller;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.PostUserReq;
import com.example.discord.src.service.UserService;
import com.example.discord.src.dto.PostUserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "users", description = "유저 등록 api")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @Operation(summary = "유저 등록", description = "닉네임을 받아 유저를 등록하고 쿠키를 반환하는 api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            @ApiResponse(responseCode = "404", description = "중복된 유저이름입니다.", content = @Content(schema = @Schema(implementation = BaseResponse.class), mediaType = "application/json"))
    })
    @PostMapping()
    public BaseResponse<PostUserRes> postUser(HttpServletResponse httpServletResponse, @RequestBody PostUserReq postUserReq) {
        try {
            PostUserRes postUserRes = userService.signUp(postUserReq);

            /* TODO: 쿠키 생성 */
            Cookie cookie = new Cookie("userId", String.valueOf(postUserRes.getUserId()));
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            return new BaseResponse<>(postUserRes);

        }catch(BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
