package com.amalitech.securesail.amalisecuresail.client.services;

import com.amalitech.securesail.amalisecuresail.client.repositories.JpaRegisteredClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClentService {
    private final JpaRegisteredClientRepository registeredClientRepository;

    public RegisteredClient getClient(String clientId) {
        return registeredClientRepository.findByClientId(clientId);
    }

}
