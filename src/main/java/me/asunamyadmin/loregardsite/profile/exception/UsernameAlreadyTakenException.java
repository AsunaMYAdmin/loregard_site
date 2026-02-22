package me.asunamyadmin.loregardsite.profile.exception;

public class UsernameAlreadyTakenException extends RuntimeException {
    public UsernameAlreadyTakenException() {
        super("The username is already taken!");
    }
}
