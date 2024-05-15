package com.amalitech.securesail.amalisecuresail.authentication.services;

import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SimpleSecurityContext;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTClaimsVerifier;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Service
public class JwtService {
    private final String secretKey = System.getenv("SECRET_KEY");
    private static final String USER_ID = "userId";
    private final String expiration = System.getenv("EXPIRATION");

    public String generateToken(User user) throws JOSEException {
        // Create JWT claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .claim(USER_ID, user.getId())
                .claim("personalEmail", user.getEmployeeEmail())
                .claim("role", user.getRole().getName())
                .claim("permissions", user.getRole().getPermissions())
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .build();

        // Create HMAC signer with the secret key
        JWSSigner signer = new MACSigner(secretKey);

        // Prepare the JWT with the claims and sign it
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        // Serialize the JWT to a string
        return signedJWT.serialize();
    }

    private ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor() {
        ConfigurableJWTProcessor<SimpleSecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
        JWKSource<SimpleSecurityContext> jweKeySource = new ImmutableSecret<>(secretKey.getBytes());
        JWSKeySelector<SimpleSecurityContext> keySelector = new JWSVerificationKeySelector<>(
                JWSAlgorithm.HS256,
                jweKeySource);
        jwtProcessor.setJWSKeySelector(keySelector);
        // Set the required JWT claims for access tokens
        jwtProcessor.setJWTClaimsSetVerifier(new DefaultJWTClaimsVerifier<>(
                new JWTClaimsSet.Builder().build(),
                new HashSet<>(Arrays.asList(
                        JWTClaimNames.ISSUED_AT,
                        JWTClaimNames.EXPIRATION_TIME,
                        USER_ID,
                        "role",
                        "permissions"))));
        return jwtProcessor;
    }


    public String extractEmail(String token) throws BadJOSEException, ParseException, JOSEException {
        JWTClaimsSet claims = jwtProcessor().process(token, null);
        return claims.getStringClaim("personalEmail");
    }

    public boolean isTokenValid(String token) throws JOSEException, ParseException {
        // Create HMAC verifier with the secret key
        JWSVerifier verifier = new MACVerifier(secretKey);

        // Parse the token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Verify the signature
        return signedJWT.verify(verifier);
    }
}
