package com.example.discord.service;

import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.controller.UserController;
import com.example.discord.src.dto.PostUserReq;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.UserRepository;
import com.example.discord.src.service.UserService;
import com.example.discord.src.dto.PostUserRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired UserController userController;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void signUp() throws Exception {
        String nickName = "오소정";
        PostUserReq postUserReq = new PostUserReq();
        postUserReq.setNickName(nickName);

        Optional<PostUserRes> postUserRes = userService.signUp(postUserReq);

        if(postUserRes.isPresent()) {
            log.info("userId: {}", postUserRes.get().getUserId());
            //log.info("cookie: {}", postUserRes.get().getCookie());

            User user = userRepository.findByNickName(nickName).get();
            assertEquals(user.getNickName(), nickName);
        }
        else {
            Optional<User> user = userRepository.findByNickName(nickName);
            assertThat(user.isPresent());
        }
    }

    @Test
    public void signUpFromController() throws Exception {
        PostUserReq postUserReq = new PostUserReq();
        postUserReq.setNickName("user2");

        BaseResponse responseBody = userController.signUp(postUserReq);

        log.info("suceess: {}", responseBody.getIsSuccess());
        log.info("code: {}", responseBody.getCode());
        log.info("message: {}", responseBody.getMessage());
        if(responseBody.getIsSuccess()) {
            PostUserRes postUserRes = (PostUserRes) responseBody.getResult();
            log.info("result: ");
            log.info("nickName: {}", postUserRes.getUserId());
            log.info("cookie: {}", postUserRes.getCookie().getValue());
        }
    }
}
