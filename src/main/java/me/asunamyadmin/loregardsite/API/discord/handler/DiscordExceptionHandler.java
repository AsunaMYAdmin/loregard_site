package me.asunamyadmin.loregardsite.API.discord.handler;

import me.asunamyadmin.loregardsite.API.discord.exception.DiscordProfileAlreadyExistException;
import me.asunamyadmin.loregardsite.API.discord.exception.DiscordUserNotFoundException;
import me.asunamyadmin.loregardsite.API.discord.exception.NullTokenException;
import me.asunamyadmin.loregardsite.global.ExceptionsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class DiscordExceptionHandler {
    @ExceptionHandler(exception = {
            NullTokenException.class,
            DiscordUserNotFoundException.class
    })
    public ResponseEntity<ExceptionsDTO> handleException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionsDTO(
                "Some not found!",
                exception.getStackTrace(),
                LocalDateTime.now()
        ));
    }

    @ExceptionHandler(DiscordProfileAlreadyExistException.class)
    public ResponseEntity<ExceptionsDTO> handleException(DiscordProfileAlreadyExistException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionsDTO(
                "Profile already exist!",
                exception.getStackTrace(),
                LocalDateTime.now()
        ));
    }
}
