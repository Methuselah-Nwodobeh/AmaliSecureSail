package com.amalitech.securesail.amalisecuresail.runners;

import com.amalitech.securesail.amalisecuresail.client.repositories.JpaRegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientSeeder {
    private final JpaRegisteredClientRepository registeredClientRepository;
    private final PasswordEncoder passwordEncoder;

    public void seedClients() {
        log.info("////////////////////////////////////////////////////////////////////////////////////");
        log.info("Seeding clients");
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("registrar-client")
				.clientSecret(passwordEncoder.encode(System.getenv("CLIENT_SECRET")))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("client.create")
                .scope("client.read")
                .build();

        registeredClientRepository.save(registeredClient);
    }
}
