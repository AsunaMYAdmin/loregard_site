package me.asunamyadmin.loregardsite.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "oauth2_clients")
public class OAuth2Client {
    @Id
    private String id;

    @Column(name = "client_id", unique = true, nullable = false)
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "redirect_uris")
    private String redirectUris;

    @Column(name = "scopes")
    private String scopes;

    @Column(name = "authorization_grant_types")
    private String authorizationGrantTypes;

    @Column(name = "client_authentication_methods")
    private String clientAuthenticationMethods;

    @Column(name = "require_authorization_consent")
    private boolean requireAuthorizationConsent = true;

    @Column(name = "access_token_time_to_live")
    private Long accessTokenTimeToLive = 3600L;

    @Column(name = "refresh_token_time_to_live")
    private Long refreshTokenTimeToLive = 86400L;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    // Конвертеры для работы с Set
    public Set<String> getRedirectUriSet() {
        return redirectUris != null ? Set.of(redirectUris.split(",")) : Set.of();
    }

    public Set<String> getScopeSet() {
        return scopes != null ? Set.of(scopes.split(",")) : Set.of();
    }

    public Set<String> getGrantTypeSet() {
        return authorizationGrantTypes != null ? Set.of(authorizationGrantTypes.split(",")) : Set.of();
    }

    public Set<String> getAuthMethodSet() {
        return clientAuthenticationMethods != null ? Set.of(clientAuthenticationMethods.split(",")) : Set.of();
    }
}
