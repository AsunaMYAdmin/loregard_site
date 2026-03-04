package me.asunamyadmin.loregardsite.global.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.asunamyadmin.loregardsite.profile.domain.Profile;
import me.asunamyadmin.loregardsite.profile.domain.ProfileService;
import me.asunamyadmin.loregardsite.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class AuthController {
    private final ProfileService profileService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(ProfileService profileService, AuthenticationManager authenticationManager) {
        this.profileService = profileService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest registerRequest, HttpServletRequest request) {
        if (!registerRequest.agree()) {
            return "redirect:/register";
        }

        Profile profile = new Profile(
          registerRequest.username(),
          registerRequest.password(),
                null,
                UserRole.USER,
                LocalDateTime.now()
        );

        profileService.createProfile(profile);
        forceAutoLogin(request, registerRequest.username(), registerRequest.password());
        return "redirect:/profile";
    }


    private void forceAutoLogin(HttpServletRequest request,
                                String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", context);
    }
}
