package me.asunamyadmin.loregardsite.API.discord.domain;

import java.time.LocalDateTime;

public record DiscordProfile(
        Integer profileID,
        String discordID,
        String discordToken,
        LocalDateTime createdAt
) {}
