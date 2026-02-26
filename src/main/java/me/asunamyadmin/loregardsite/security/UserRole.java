package me.asunamyadmin.loregardsite.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
    USER,
    MODERATOR,
    ADMIN,
    GAME_MASTER,
    CARDINAL_SYSTEM;

    public SimpleGrantedAuthority getSimpleGrantedAuthority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
