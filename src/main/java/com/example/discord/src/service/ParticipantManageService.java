package com.example.discord.src.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ParticipantManageService {
    private static final HashMap<String, Set<String>> channelParticipants = new HashMap<>();

    public synchronized List<String> addParticipants(String channelId, String userKey){
        Set<String> participants = channelParticipants.computeIfAbsent(channelId, k -> new HashSet<>());
        participants.add(userKey);
        return participants.stream().toList();
    }

    public synchronized List<String> removeParticipants(String channelId, String userKey){
        Set<String> participants = channelParticipants.computeIfAbsent(channelId, k -> new HashSet<>());
        participants.remove(userKey);
        return participants.stream().toList();
    }

    public List<String> getParticipants(String channelId){
        Set<String> participants = channelParticipants.getOrDefault(channelId, new HashSet<>());
        return participants.stream().toList();
    }
}
