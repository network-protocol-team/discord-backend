package com.example.discord.src.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Getter
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long messageId;

    private Long userId;

    private Long channelId;

    private String content;

    private Date createdAt;
}
