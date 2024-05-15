package com.amalitech.securesail.amalisecuresail.authentication.services;

import com.amalitech.securesail.amalisecuresail.authentication.models.BaseUserDetails;
import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import com.amalitech.securesail.amalisecuresail.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.findUserByEmployeeEmail(email);
        return userInfo.map(BaseUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}