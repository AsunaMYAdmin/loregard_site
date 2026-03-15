package me.asunamyadmin.loregardsite.API.discord.tokens.service;

import lombok.RequiredArgsConstructor;
import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileEntity;
import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileRepository;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordUserNotFoundException;
import me.asunamyadmin.loregardsite.API.discord.tokens.exception.TokenAlreadyCreatedException;
import me.asunamyadmin.loregardsite.API.discord.tokens.exception.TokenNotFoundException;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final DiscordProfileRepository discordProfileRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public String generate(String username) {
        return generateToken(username, true);
    }

    @Transactional
    public String changeToken(String username) {
        return generateToken(username, false);
    }

    public String getTokenByUserName(String username) {
        return getDiscordProfileByUserName(username).getDiscordToken();
    }

    private String generateNewToken() {
        String token;
        do {
            token = TokenGenerator.generateToken();
        } while (discordProfileRepository.findByDiscordToken(token).isPresent());
        return token;
    }

    private DiscordProfileEntity getDiscordProfileByUserName(String username) {
        int id = profileRepository.findByUsername(username).orElseThrow(ProfileNotFoundException::new).getId();
        return discordProfileRepository.findByProfileID(id)
                .orElseThrow(DiscordUserNotFoundException::new);
    }

    private String generateToken(String username, boolean isNewToken) {
        DiscordProfileEntity entity = getDiscordProfileByUserName(username);
        if (isNewToken) {
            if (entity.getDiscordToken() != null) throw new TokenAlreadyCreatedException();
        }
        if (!isNewToken) {
            if (entity.getDiscordToken() == null) throw new TokenNotFoundException();
        }
        String token = generateNewToken();
        entity.setDiscordToken(token);
        discordProfileRepository.save(entity);
        return token;
    }
}
