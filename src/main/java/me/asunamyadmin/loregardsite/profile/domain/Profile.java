package me.asunamyadmin.loregardsite.profile.domain;

import jakarta.validation.constraints.NotNull;
import me.asunamyadmin.loregardsite.security.UserRole;

import java.time.LocalDateTime;

public record Profile(
        @NotNull
        String username,
        @NotNull
        String password,
        Integer accountNumber,
        UserRole role,
        LocalDateTime createdAt
) {
}
