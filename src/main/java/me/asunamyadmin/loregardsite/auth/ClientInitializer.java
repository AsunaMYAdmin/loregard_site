package me.asunamyadmin.loregardsite.auth;

import me.asunamyadmin.loregardsite.auth.service.OAuth2ClientService;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ClientInitializer implements CommandLineRunner {
    @Autowired
    private OAuth2ClientService clientService;

    @Override
    public void run(String @NonNull ... args) {
        // Проверяем, есть ли уже клиент
        try {
            clientService.ignoreValidateClientSecret("bank-client", "rweN.LMaP046/I^xL");
            System.out.println("Client already exists");
        } catch (Exception e) {
            // Создаем клиента с bcrypt паролем
            clientService.ignoreCreateClient(
                    "bank-client",
                    "rweN.LMaP046/I^xL",  // Этот паль будет закодирован bcrypt
                    "Bank Application",
                    new String[]{"https://bank.loregard.ru/login/oauth2/code/site", "http://localhost:8081/login/oauth2/code/site"},
                    new String[]{"openid", "profile", "email"}
            );
            System.out.println("Test client created with bcrypt password");
        }
    }
}
