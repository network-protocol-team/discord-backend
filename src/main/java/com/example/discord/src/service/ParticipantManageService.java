package com.example.discord.src.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParticipantManageService {
    private static HashMap<String, Set<String>> channelParticipants = new HashMap<>();

    public List<String> addParticipants(String channelId, String userKey){
        Set<String> participants = channelParticipants.getOrDefault(channelId, new HashSet<>());
        participants.add(userKey);
        return participants.stream().toList();
    }

    public List<String> removeParticipants(String channelId, String userKey){
        Set<String> participants = channelParticipants.getOrDefault(channelId, new HashSet<>());
        participants.remove(userKey);
        return participants.stream().toList();
    }

    public List<String> getParticipants(String channelId){
        Set<String> participants = channelParticipants.getOrDefault(channelId, new HashSet<>());
        return participants.stream().toList();
    }
}
