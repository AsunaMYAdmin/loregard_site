package me.asunamyadmin.loregardsite.API.discord.exception;

public class DiscordProfileAlreadyExistException extends RuntimeException {
    public DiscordProfileAlreadyExistException() {
        super("Discord Profile Already Exists!");
    }
}
