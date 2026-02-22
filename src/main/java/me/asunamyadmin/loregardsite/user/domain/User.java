package me.asunamyadmin.loregardsite.user.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record User(
        Integer id,
        @NotNull
        @Email
        String email,
        @NotNull
        String password,
        LocalDateTime createdAt
) {
}
