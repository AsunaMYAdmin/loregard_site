package me.asunamyadmin.loregardsite.API.discord.controller;

import me.asunamyadmin.loregardsite.API.discord.domain.TokenService;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordProfileAlreadyExistException;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TokenController {
    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generateToken() {
        try {
            String token = tokenService.createToken();
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
        } catch (ProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Профиль не найден");
        } catch (DiscordProfileAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Discord уже привязан к аккаунту");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при создании привязки: " + e.getMessage());
        }
    }
}
