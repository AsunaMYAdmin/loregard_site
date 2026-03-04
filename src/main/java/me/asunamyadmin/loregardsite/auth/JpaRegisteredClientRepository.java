package me.asunamyadmin.loregardsite.auth;

import me.asunamyadmin.loregardsite.auth.entity.OAuth2Client;
import me.asunamyadmin.loregardsite.auth.repository.OAuth2ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class JpaRegisteredClientRepository implements RegisteredClientRepository {
    @Autowired
    private OAuth2ClientRepository clientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        // Этот метод обычно используется для динамической регистрации клиентов
        // Но мы будем использовать наш сервис для создания
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(id)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    private RegisteredClient toRegisteredClient(OAuth2Client entity) {
        return RegisteredClient.withId(entity.getId())
                .clientId(entity.getClientId())
                .clientSecret(entity.getClientSecret()) // Уже закодирован!
                .clientName(entity.getClientName())
                .redirectUris(uris -> uris.addAll(entity.getRedirectUriSet()))
                .scopes(scopes -> scopes.addAll(entity.getScopeSet()))
                .clientAuthenticationMethods(methods ->
                        entity.getAuthMethodSet().forEach(m ->
                                methods.add(new ClientAuthenticationMethod(m))))
                .authorizationGrantTypes(grants ->
                        entity.getGrantTypeSet().forEach(g ->
                                grants.add(new AuthorizationGrantType(g))))
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofSeconds(entity.getAccessTokenTimeToLive()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(entity.getRefreshTokenTimeToLive()))
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(entity.isRequireAuthorizationConsent())
                        .build())
                .postLogoutRedirectUri("https://bank.loregard.ru/")
                .build();
    }
}
