package me.asunamyadmin.loregardsite.global;

import java.time.LocalDateTime;

public record ExceptionsDTO(
        String title,
        StackTraceElement[] stackTrace,
        LocalDateTime errorTime
) {
}
