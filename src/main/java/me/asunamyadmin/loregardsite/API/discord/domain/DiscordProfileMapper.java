package me.asunamyadmin.loregardsite.API.discord.domain;

import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class DiscordProfileMapper {
    public DiscordProfileEntity discordProfileToEntity(DiscordProfile discordProfile) {
        DiscordProfileEntity discordProfileEntity = new DiscordProfileEntity();
        discordProfileEntity.setDiscordID(discordProfile.discordID());
        discordProfileEntity.setProfileID(discordProfile.profileID());
        return discordProfileEntity;
    }

    public DiscordProfile entityToProfile(DiscordProfileEntity entity) {
        return new  DiscordProfile(
                entity.getProfileID(),
                entity.getDiscordID(),
                entity.getDiscordToken(),
                entity.getCreatedAt()
        );
    }
}
