package me.asunamyadmin.loregardsite.API.discord.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscordProfileRepository extends JpaRepository<DiscordProfileEntity, Integer> {
    boolean existsByProfileID(Integer discordProfileID);
    Optional<DiscordProfileEntity> findByProfileID(Integer profileID);
    Optional<DiscordProfileEntity> findByDiscordToken(String discordToken);

    void deleteDiscordProfileEntityByProfileID(Integer profileID);
}
