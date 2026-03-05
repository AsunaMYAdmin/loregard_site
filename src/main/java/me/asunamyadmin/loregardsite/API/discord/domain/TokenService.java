package me.asunamyadmin.loregardsite.API.discord.domain;

import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileEntity;
import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileRepository;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordProfileAlreadyExistException;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordUserNotFoundException;
import me.asunamyadmin.loregardsite.API.discord.security.TokenGenerator;
import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenService {
    private final DiscordProfileRepository discordProfileRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public TokenService(DiscordProfileRepository discordProfileRepository, ProfileRepository profileRepository) {
        this.discordProfileRepository = discordProfileRepository;
        this.profileRepository = profileRepository;
    }

//    @Transactional
//    public String createToken() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        assert authentication != null;
//        String username = authentication.getName();
//        ProfileEntity profile = profileRepository.findProfileByUsername(username)
//                .orElseThrow(ProfileNotFoundException::new);
//        int accountNumber = profile.getAccountNumber();
//        if (discordProfileRepository.findByProfileID(accountNumber).isPresent()) {
//            throw new DiscordProfileAlreadyExistException();
//        }
//        DiscordProfileEntity profileEntity = new DiscordProfileEntity();
//        profileEntity.setProfileID(accountNumber);
//        String token;
//        while (true) {
//            try {
//                token = TokenGenerator.generateToken();
//                profileEntity.setDiscordToken(token);
//                discordProfileRepository.save(profileEntity);
//                break;
//            } catch (DataIntegrityViolationException ignored) {
//                // токен совпал — пробуем ещё раз
//            }
//        }
//        return token;
//    }

    @Transactional
    public String createToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();

        System.out.println("Looking for profile with username: " + username);

        // Ищем профиль по username
        ProfileEntity profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("Profile not found for username: " + username);
                    return new ProfileNotFoundException();
                });

        System.out.println("Profile found with account number: " + profile.getAccountNumber());

        int accountNumber = profile.getAccountNumber();

        // Проверяем, не привязан ли уже Discord
        if (discordProfileRepository.findByProfileID(accountNumber).isPresent()) {
            System.out.println("Discord already linked for account: " + accountNumber);
            throw new DiscordProfileAlreadyExistException();
        }

        // Создаём новую привязку
        DiscordProfileEntity profileEntity = new DiscordProfileEntity();
        profileEntity.setProfileID(accountNumber);

        String token;
        while (true) {
            try {
                token = TokenGenerator.generateToken();
                profileEntity.setDiscordToken(token);
                discordProfileRepository.save(profileEntity);
                System.out.println("Token generated and saved: " + token);
                break;
            } catch (DataIntegrityViolationException ignored) {
                System.out.println("Token collision, retrying...");
            }
        }
        return token;
    }

    @Transactional
    public String changeToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        ProfileEntity profile = profileRepository.findProfileByUsername(username)
                .orElseThrow(ProfileNotFoundException::new);
        DiscordProfileEntity entity = discordProfileRepository.findByProfileID(profile.getAccountNumber())
                .orElseThrow(DiscordUserNotFoundException::new);
        String token;
        while (true) {
            try {
                token = TokenGenerator.generateToken();
                entity.setDiscordToken(token);
                discordProfileRepository.save(entity);
                break;
            } catch (DataIntegrityViolationException ignored) {}
        }
        return token;
    }
}
