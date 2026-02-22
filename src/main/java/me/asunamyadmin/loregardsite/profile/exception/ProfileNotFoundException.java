package me.asunamyadmin.loregardsite.profile.exception;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException() {
        super("Profile not found!");
    }
}
