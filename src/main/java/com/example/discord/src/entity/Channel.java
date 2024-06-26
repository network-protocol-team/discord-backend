package com.example.discord.src.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "channel_id", columnDefinition = "BINARY(16)")
    private UUID channelId;

    @Setter
    @Column(name = "name", nullable = false)
    private String channelName;

    @OneToMany(mappedBy = "channel")
    private List<Message> messageList = new ArrayList<>();

    public Channel(String channelName) {
        this.channelName = channelName;
    }
}
