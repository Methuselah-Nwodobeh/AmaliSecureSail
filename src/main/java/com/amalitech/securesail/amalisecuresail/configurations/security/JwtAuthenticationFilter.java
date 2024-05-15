package com.amalitech.securesail.amalisecuresail.configurations.security;

import com.amalitech.securesail.amalisecuresail.authentication.services.JwtService;
import com.amalitech.securesail.amalisecuresail.authentication.exceptions.JWTOpsFailedException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (shouldIgnore(request)) {
            filterChain.doFilter(request, response);
        } else {
            //get authorization token from request header
            Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
            authHeader.filter(h -> h.startsWith("Bearer "))
                    .map(h -> h.substring(7))
                    .ifPresent(token -> {
                        Optional<String> usernameOptional;
                        try {
                            usernameOptional = Optional.ofNullable(jwtService.extractEmail(token));
                        } catch (BadJOSEException | JOSEException | ParseException e) {
                            throw new JWTOpsFailedException(e.getMessage());
                        }
                        // get user details from database
                        usernameOptional.filter(username -> SecurityContextHolder.getContext().getAuthentication() == null)
                                .ifPresent(username -> {
                                    UserDetails userDetails = userService.loadUserByUsername(username);
                                    // check if token is valid
                                    try {
                                        if (Boolean.TRUE.equals(jwtService.isTokenValid(token))) {
                                            UsernamePasswordAuthenticationToken authenticationToken =
                                                    new UsernamePasswordAuthenticationToken(userDetails,
                                                            null,
                                                            userDetails.getAuthorities());
                                            // enforce authentication token with details of request
                                            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                            // Update SecurityContextHolder or authentication token
                                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                        }
                                    } catch (JOSEException | ParseException e) {
                                        throw new JWTOpsFailedException(e.getMessage());
                                    }
                                });
                    });
            //add filter chain
            filterChain.doFilter(request, response);
        }
    }

    private boolean shouldIgnore(HttpServletRequest request) {
        // Define the list of paths to be ignored (e.g., client registration endpoints)
        List<String> ignoredPaths = Arrays.asList("/oauth2/register", "/oauth2/token");

        // Check if the request path matches any of the ignored paths
        String requestPath = request.getRequestURI();
        return ignoredPaths.contains(requestPath);
    }
}