package com.example.discord.src.service;

import com.example.discord.common.exception.BaseException;
import com.example.discord.common.response.ApiResponseStatus;
import com.example.discord.src.dto.GetMessageRes;
import com.example.discord.src.entity.Channel;
import com.example.discord.src.entity.Message;
import com.example.discord.src.entity.User;
import com.example.discord.src.repository.ChannelRepository;
import com.example.discord.src.repository.MessageRepository;
import com.example.discord.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    @Transactional
    public GetMessageRes storeMessage(String nickName, UUID channelId, String content) {
        Optional<User> user = userRepository.findByNickName(nickName);
        Optional<Channel> channel = channelRepository.findById(channelId);

        if(user.isEmpty())
            throw new BaseException(ApiResponseStatus.NO_EXIST_USER_ERROR);

        if(channel.isEmpty())
            throw new BaseException(ApiResponseStatus.NO_EXIST_CHANNEL_ERROR);

        Message message = new Message(content);
        message.addUser(user.get());
        message.addChannel(channel.get());
        messageRepository.save(message);

        GetMessageRes getMessageRes = GetMessageRes.builder()
                .nickName(message.getUser().getNickName())
                .createdAt(message.getCreatedAt())
                .content(message.getContent())
                .build();

        return getMessageRes;
    }

}
