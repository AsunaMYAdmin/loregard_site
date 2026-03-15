package me.asunamyadmin.loregardsite.API.discord.tokens.controller;

import lombok.RequiredArgsConstructor;
import me.asunamyadmin.loregardsite.API.discord.tokens.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/settings")
public class SettingsMVC {
    private final TokenService tokenService;

    @GetMapping
    public String settingsPage(Principal principal, Model model) {
        String token = tokenService.getTokenByUserName(principal.getName());
        model.addAttribute("token", token);
        return "settings";
    }
}
