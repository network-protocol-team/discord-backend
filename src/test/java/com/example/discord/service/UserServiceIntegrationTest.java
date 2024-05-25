package com.example.discord.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.src.dto.PostUserRes;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.UserRepository;
import com.example.discord.src.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserServiceIntegrationTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    public void signUp() {
        String nickName = "user2";

        try {
            PostUserRes postUserRes = userService.signUp(nickName);
            log.info("userId: {}", postUserRes.getUserId());
            log.info("nickName: {}", postUserRes.getNickName());

            User user = userRepository.findByNickName(nickName).get();
            Assertions.assertEquals(user.getNickName(), postUserRes.getNickName());

        }catch(BaseException baseException) {
            log.info("status: {}", baseException.getStatus());
            log.info("message: {}", baseException.getMessage());

            Assertions.assertEquals(true, userRepository.findByNickName(nickName).isPresent());
        }
    }
}
