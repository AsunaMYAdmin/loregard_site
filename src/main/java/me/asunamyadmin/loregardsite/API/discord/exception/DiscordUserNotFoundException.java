package me.asunamyadmin.loregardsite.API.discord.exception;

public class DiscordUserNotFoundException extends RuntimeException {
    public DiscordUserNotFoundException() {
        super("Discord User Not Found!");
    }
}
