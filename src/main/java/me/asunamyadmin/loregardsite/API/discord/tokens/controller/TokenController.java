package me.asunamyadmin.loregardsite.API.discord.tokens.controller;

import lombok.RequiredArgsConstructor;
import me.asunamyadmin.loregardsite.API.discord.tokens.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/generate")
    public String generateToken(Principal principal, RedirectAttributes attributes) {
        return getTokenAndRedirect(principal, attributes,true);
    }

    @PostMapping("/change")
    public String changeToken(Principal principal, RedirectAttributes attributes) {
        return getTokenAndRedirect(principal, attributes,false);
    }
    
    private String getTokenAndRedirect(Principal principal, RedirectAttributes attributes, boolean isNewToken) {
        String token = isNewToken
                ? tokenService.generate(principal.getName())
                : tokenService.changeToken(principal.getName());
        attributes.addFlashAttribute("token", token);
        return "redirect:/settings";
    }
}
