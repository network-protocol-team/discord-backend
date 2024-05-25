package com.example.discord.src.repository;

import com.example.discord.src.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, UUID> {
    Optional<Channel> findByChannelName(String channelName);
}
