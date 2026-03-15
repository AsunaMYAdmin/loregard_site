package me.asunamyadmin.loregardsite.API.discord.tokens.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token not found");
    }
}
