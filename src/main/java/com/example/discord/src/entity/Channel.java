package com.example.discord.src.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
