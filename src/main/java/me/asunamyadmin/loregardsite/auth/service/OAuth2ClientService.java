package me.asunamyadmin.loregardsite.auth.service;

import me.asunamyadmin.loregardsite.auth.entity.OAuth2Client;
import me.asunamyadmin.loregardsite.auth.repository.OAuth2ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OAuth2ClientService {
   @Autowired
   private OAuth2ClientRepository clientRepository;

   @Autowired
   private BCryptPasswordEncoder passwordEncoder;

   public OAuth2Client ignoreCreateClient(String clientId, String rawSecret, String clientName,
                                    String[] redirectUris, String[] scopes) {
       OAuth2Client client = new OAuth2Client();
       client.setId(UUID.randomUUID().toString());
       client.setClientId(clientId);

       // Кодируем секрет перед сохранением!
       client.setClientSecret(passwordEncoder.encode(rawSecret));

       client.setClientName(clientName);
       client.setRedirectUris(String.join(",", redirectUris));
       client.setScopes(String.join(",", scopes));
       client.setAuthorizationGrantTypes("authorization_code,refresh_token");
       client.setClientAuthenticationMethods("client_secret_basic,client_secret_post");

       return clientRepository.save(client);
   }

    @Transactional
    public OAuth2Client ignoreUpdateClientSecret(String clientId, String newRawSecret) {
        OAuth2Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Кодируем новый секрет
        client.setClientSecret(passwordEncoder.encode(newRawSecret));

        return clientRepository.save(client);
    }

    public boolean ignoreValidateClientSecret(String clientId, String rawSecret) {
        OAuth2Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Сравниваем raw секрет с закодированным в БД
        return passwordEncoder.matches(rawSecret, client.getClientSecret());
    }
}
