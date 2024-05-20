package com.example.discord.src.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Channel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long channelId;

    @Column(name = "name", nullable = false)
    private String channelName;

    @OneToMany(mappedBy = "channel")
    private List<Message> messageList = new ArrayList<>();

    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
