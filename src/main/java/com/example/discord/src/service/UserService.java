package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.src.dto.PostUserReq;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.UserRepository;
import com.example.discord.src.dto.PostUserRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public PostUserRes signUp(PostUserReq postUserReq){

        if(validateDuplicateNickName(postUserReq.getNickName())) {
            // nickname 중복
            throw new BaseException(ApiResponseStatus.DUPLICATE_NICKNAME_ERROR);
        }

        User user = new User();
        user.setNickName(postUserReq.getNickName());
        userRepository.save(user);

        PostUserRes postUserRes = new PostUserRes();
        postUserRes.setUserId(user.getUserId());
        postUserRes.setNickName(user.getNickName());
        log.info("user " + user.getNickName() + " created");

        return postUserRes;
    }
}
