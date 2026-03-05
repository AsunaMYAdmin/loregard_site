package me.asunamyadmin.loregardsite.API.discord.controller;

import me.asunamyadmin.loregardsite.API.discord.domain.DiscordProfile;
import me.asunamyadmin.loregardsite.API.discord.domain.DiscordService;
import me.asunamyadmin.loregardsite.API.discord.domain.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discord/profile")
public class DiscordProfileController {
    DiscordService discordService;
    TokenService tokenService;

    @Autowired
    public DiscordProfileController(DiscordService discordService, TokenService tokenService) {
        this.discordService = discordService;
        this.tokenService = tokenService;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DiscordProfile> getDiscordProfile(@PathVariable Integer id) {
        return ResponseEntity.ok().body(discordService.getByProfileId(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiscordProfile>> getAllDiscordProfiles() {
        return ResponseEntity.ok().body(discordService.getAllDiscordProfiles());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createDiscordProfile(
            @RequestBody String token,
            @RequestBody String discordId) {
        discordService.saveDiscordProfile(token, discordId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteDiscordProfile() {
        discordService.deleteDiscordProfile();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/newToken")
    public ResponseEntity<String> newToken() {
        String token = tokenService.changeToken();
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/checkToken")
    public ResponseEntity<Boolean> checkToken(@RequestBody String token) {
        return ResponseEntity.ok(discordService.isValidToken(token));
    }

}
