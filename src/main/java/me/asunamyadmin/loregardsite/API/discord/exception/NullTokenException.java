package me.asunamyadmin.loregardsite.API.discord.exception;

public class NullTokenException extends RuntimeException {
    public NullTokenException() {
        super("Token is not found!");
    }
}
