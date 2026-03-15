package me.asunamyadmin.loregardsite.API.discord.tokens.service;


import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    public static String generateToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytesForToken = new byte[32];
        random.nextBytes(bytesForToken);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(bytesForToken);
        return rawToken.substring(0, 3).toUpperCase() + "-" +
                rawToken.substring(3, 6).toUpperCase() + "-" +
                rawToken.substring(6, 9).toUpperCase();
    }
}
