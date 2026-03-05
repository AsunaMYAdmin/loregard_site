package me.asunamyadmin.loregardsite.config;

import me.asunamyadmin.loregardsite.API.discord.security.BotAuthenticationFilter;
import me.asunamyadmin.loregardsite.profile.data.ProfileEntity;
import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.domain.Profile;
import me.asunamyadmin.loregardsite.profile.domain.ProfileMapper;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final ProfileRepository profileRepository;
    @Value("${discord.bot.token}")
    private String botToken;
    private final ProfileMapper mapper;

    @Autowired
    public SecurityConfig(ProfileRepository profileRepository, ProfileMapper mapper) {
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/discord/**"))
                .addFilterBefore(new BotAuthenticationFilter(botToken), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth.requestMatchers("/",  "/index",
                                 "/css/**", "/js/**", "/images/**", "/about", "/heroes",
                                "/bank", "/forum", "/faq", "/staff", "/register", "/login",
                                "/error/**", "/error")
                        .permitAll()
                        .requestMatchers("/api/discord/**").authenticated()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .securityContext(context -> context
                        .securityContextRepository(new HttpSessionSecurityContextRepository())
                )
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            ProfileEntity entity =
                    profileRepository.findProfileByUsername(username).orElseThrow(ProfileNotFoundException::new);
            Profile profile = mapper.mapProfileEntityToProfile(entity);
            Set<SimpleGrantedAuthority> roles = Collections.singleton(profile.role().getSimpleGrantedAuthority());
            return new User(profile.username(), profile.password(), roles);
        };
    }
}