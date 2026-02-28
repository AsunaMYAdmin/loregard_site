package me.asunamyadmin.loregardsite.config;

import me.asunamyadmin.loregardsite.profile.data.ProfileRepository;
import me.asunamyadmin.loregardsite.profile.domain.Profile;
import me.asunamyadmin.loregardsite.profile.exception.ProfileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ProfileRepository profileRepository;

    @Autowired
    public SecurityConfig(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .authorizeHttpRequests(auth -> auth.requestMatchers("/",  "/index",
                                 "/css/**", "/js/**", "/images/**", "/about", "/heroes",
                                "/bank", "/forum", "/faq", "/staff", "/register", "/login",
                                "/error/**", "/error")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .build();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll())
//                .build();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Profile profile =
                    profileRepository.findProfileByUsername(username).orElseThrow(ProfileNotFoundException::new);
            Set<SimpleGrantedAuthority> roles = Collections.singleton(profile.role().getSimpleGrantedAuthority());
            return new User(profile.username(), profile.password(), roles);
        };
    }
}