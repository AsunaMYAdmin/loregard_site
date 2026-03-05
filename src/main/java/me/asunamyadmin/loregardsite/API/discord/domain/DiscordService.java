package me.asunamyadmin.loregardsite.API.discord.domain;

import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileEntity;
import me.asunamyadmin.loregardsite.API.discord.data.DiscordProfileRepository;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordProfileAlreadyExistException;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordUserNotFoundException;
import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscordService {
    private final DiscordProfileRepository discordProfileRepository;
    private final DiscordProfileMapper mapper;
    private final ProfileRepository profileRepository;

    @Autowired
    public DiscordService(DiscordProfileRepository discordProfileRepository, DiscordProfileMapper mapper
                ,ProfileRepository profileRepository) {
        this.discordProfileRepository = discordProfileRepository;
        this.mapper = mapper;
        this.profileRepository = profileRepository;
    }

    public DiscordProfile getByProfileId(int profileId) {
        DiscordProfileEntity entity = discordProfileRepository.findByProfileID(profileId)
                .orElseThrow(DiscordUserNotFoundException::new);
        return mapper.entityToProfile(entity);
    }

    public List<DiscordProfile> getAllDiscordProfiles() {
        return discordProfileRepository.findAll().stream()
                .map(mapper::entityToProfile)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveDiscordProfile(String token, String discordId) {
        DiscordProfileEntity profile = discordProfileRepository.findByDiscordToken(token)
                .orElseThrow(DiscordUserNotFoundException::new);
        if (profile.getDiscordID() != null) {
            throw new DiscordProfileAlreadyExistException();
        }
        profile.setDiscordID(discordId);
        discordProfileRepository.save(profile);
    }

    @Transactional
    public void deleteDiscordProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        ProfileEntity profile = profileRepository.findByUsername(username)
                .orElseThrow(ProfileNotFoundException::new);
        discordProfileRepository.deleteDiscordProfileEntityByProfileID(profile.getAccountNumber());
    }


    public boolean isValidToken(String token) {
        return discordProfileRepository.findByDiscordToken(token).isPresent();
    }
}
