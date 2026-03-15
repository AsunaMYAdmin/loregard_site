package me.asunamyadmin.loregardsite.API.discord.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "discord_links")
public class DiscordProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer ID;
    @Column(name = "profile_id")
    Integer profileID;
    @Column(name = "discord_id")
    String discordID;
    @Column(name = "discord_token")
    String discordToken;
    LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
