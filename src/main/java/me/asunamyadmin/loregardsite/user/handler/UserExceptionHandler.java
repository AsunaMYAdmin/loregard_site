package me.asunamyadmin.loregardsite.user.handler;

import me.asunamyadmin.loregardsite.global.ExceptionsDTO;
import me.asunamyadmin.loregardsite.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionsDTO> handleUserNotFoundException(UserNotFoundException userEx){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
               new ExceptionsDTO(
                       "USER NOT FOUND!",
                       userEx.getStackTrace(),
                       LocalDateTime.now()
               )
       );
    }
}
