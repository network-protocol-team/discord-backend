package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.common.response.BaseResponse;
import com.example.discord.src.dto.PostUserReq;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.UserRepository;
import com.example.discord.src.dto.PostUserRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /* nickname 중복 체크 */
    private Boolean validateDuplicateNickName(String nickName) {
        return userRepository.findByNickName(nickName).isPresent();
    }

    /* 유저 가입 */
    public Optional<PostUserRes> signUp(PostUserReq postUserReq){

        if(validateDuplicateNickName(postUserReq.getNickName())) {
            // nickname 중복
            return Optional.empty();
        }

        User user = new User();
        user.setNickName(postUserReq.getNickName());
        userRepository.save(user);

        PostUserRes postUserRes = new PostUserRes();
        postUserRes.setUserId(user.getUserId());
        log.info("user " + user.getNickName() + " created");

        return Optional.of(postUserRes);
    }

}
