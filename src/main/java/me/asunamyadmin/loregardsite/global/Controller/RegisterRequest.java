package me.asunamyadmin.loregardsite.global.Controller;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull String username,
        @NotNull String password,
        boolean agree
) {
}
