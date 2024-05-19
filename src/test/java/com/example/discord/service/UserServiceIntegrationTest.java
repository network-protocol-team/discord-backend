package com.example.discord.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.controller.UserController;
import com.example.discord.src.dto.PostUserReq;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.UserRepository;
import com.example.discord.src.service.UserService;
import com.example.discord.src.dto.PostUserRes;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

@Slf4j
@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired UserController userController;
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void signUp() {
        String nickName = "오소정";
        PostUserReq postUserReq = new PostUserReq(nickName);

        try {
            PostUserRes postUserRes = userService.signUp(postUserReq);
            log.info("userId: {}", postUserRes.getUserId());

            User user = userRepository.findByNickName(nickName).get();
            Assertions.assertEquals(user.getNickName(), nickName);

        }catch(BaseException baseException) {
            log.info("status: {}", baseException.getStatus());
            log.info("message: {}", baseException.getMessage());

            Assertions.assertEquals(true, userRepository.findByNickName(nickName).isPresent());
        }
    }
}
