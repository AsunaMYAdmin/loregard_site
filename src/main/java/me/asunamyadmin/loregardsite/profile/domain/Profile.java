package me.asunamyadmin.loregardsite.profile.domain;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Profile(
        @NotNull
        String username,
        @NotNull
        int userId,
        @NotNull
        int accountNumber,
        @NotNull
        int gameID,
        LocalDateTime createdAt
) {
}
