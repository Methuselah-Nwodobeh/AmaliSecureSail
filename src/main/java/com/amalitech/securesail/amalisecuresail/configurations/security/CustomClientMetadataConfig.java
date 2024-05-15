package com.amalitech.securesail.amalisecuresail.configurations.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.oidc.OidcClientRegistration;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientConfigurationAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcClientRegistrationAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.oidc.converter.OidcClientRegistrationRegisteredClientConverter;
import org.springframework.security.oauth2.server.authorization.oidc.converter.RegisteredClientOidcClientRegistrationConverter;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.util.CollectionUtils;

public class CustomClientMetadataConfig {

    private CustomClientMetadataConfig() {
    }

    public static Consumer<List<AuthenticationProvider>> configureCustomClientMetadataConverters() {
        List<String> customClientMetadata = List.of("logo_uri", "contacts");

        return authenticationProviders -> {
            CustomRegisteredClientConverter registeredClientConverter =
                    new CustomRegisteredClientConverter(customClientMetadata);
            CustomClientRegistrationConverter clientRegistrationConverter =
                    new CustomClientRegistrationConverter(customClientMetadata);

            authenticationProviders.forEach(authenticationProvider -> {
                if (authenticationProvider instanceof OidcClientRegistrationAuthenticationProvider provider) {
                    provider.setRegisteredClientConverter(registeredClientConverter);
                    provider.setClientRegistrationConverter(clientRegistrationConverter);
                }
                if (authenticationProvider instanceof OidcClientConfigurationAuthenticationProvider provider) {
                    provider.setClientRegistrationConverter(clientRegistrationConverter);
                }
            });
        };
    }

    private static class CustomRegisteredClientConverter
            implements Converter<OidcClientRegistration, RegisteredClient> {

        private final List<String> customClientMetadata;
        private final OidcClientRegistrationRegisteredClientConverter delegate;

        private CustomRegisteredClientConverter(List<String> customClientMetadata) {
            this.customClientMetadata = customClientMetadata;
            this.delegate = new OidcClientRegistrationRegisteredClientConverter();
        }

        @Override
        public RegisteredClient convert(OidcClientRegistration clientRegistration) {
            RegisteredClient registeredClient = this.delegate.convert(clientRegistration);
            assert registeredClient != null;
            ClientSettings.Builder clientSettingsBuilder = ClientSettings.withSettings(
                    registeredClient.getClientSettings().getSettings());
            if (!CollectionUtils.isEmpty(this.customClientMetadata)) {
                clientRegistration.getClaims().forEach((claim, value) -> {
                    if (this.customClientMetadata.contains(claim)) {
                        clientSettingsBuilder.setting(claim, value);
                    }
                });
            }

            return RegisteredClient.from(registeredClient)
                    .clientSettings(clientSettingsBuilder.build())
                    .build();
        }
    }

    private static class CustomClientRegistrationConverter
            implements Converter<RegisteredClient, OidcClientRegistration> {

        private final List<String> customClientMetadata;
        private final RegisteredClientOidcClientRegistrationConverter delegate;

        private CustomClientRegistrationConverter(List<String> customClientMetadata) {
            this.customClientMetadata = customClientMetadata;
            this.delegate = new RegisteredClientOidcClientRegistrationConverter();
        }

        @Override
        public OidcClientRegistration convert(RegisteredClient registeredClient) {
            OidcClientRegistration clientRegistration = this.delegate.convert(registeredClient);
            assert clientRegistration != null;
            Map<String, Object> claims = new HashMap<>(clientRegistration.getClaims());
            if (!CollectionUtils.isEmpty(this.customClientMetadata)) {
                ClientSettings clientSettings = registeredClient.getClientSettings();
                claims.putAll(this.customClientMetadata.stream()
                        .filter(metadata -> clientSettings.getSetting(metadata) != null)
                        .collect(Collectors.toMap(Function.identity(), clientSettings::getSetting)));
            }

            return OidcClientRegistration.withClaims(claims).build();
        }

    }

}