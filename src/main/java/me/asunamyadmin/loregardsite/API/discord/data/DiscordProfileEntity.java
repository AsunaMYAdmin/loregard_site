package me.asunamyadmin.loregardsite.API.discord.data;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "discord_links")
public class DiscordProfileEntity {
    @Id
    Integer ID;
    Integer profileID;
    @Column(name = "discord_id")
    String discordID;
    String discordToken;
    LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void setProfileID(Integer profileID) {
        if (profileID == null) {
            this.profileID = profileID;
        }
    }

    public void setDiscordID(String discordID) {
        if (discordID == null) {
            this.discordID = discordID;
        }
    }

    public void setDiscordToken(String discordToken) {
        if (discordToken == null) {
            this.discordToken = discordToken;
        }
    }


}
