package me.asunamyadmin.loregardsite.API.discord.tokens.exception;

public class TokenAlreadyCreatedException extends RuntimeException {
    public TokenAlreadyCreatedException() {
        super("Token is already created!");
    }
}
