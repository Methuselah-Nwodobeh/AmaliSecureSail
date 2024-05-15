package com.amalitech.securesail.amalisecuresail.authentication.models;

import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class BaseUserDetails implements UserDetails {
    private  String username;
    private  String password;
    private transient User baseUserModel;
    private String role;
    @Getter
    private List<String> permissions;
    @Getter
    private Boolean isAdmin;

    public BaseUserDetails(User baseUserModel){
        this.username = baseUserModel.getEmployeeEmail();
        this.password = baseUserModel.getPassword();
        this.baseUserModel = baseUserModel;
        this.role = baseUserModel.getRole().getName();
        this.permissions = baseUserModel.getRole().getPermissions();
        this.isAdmin = baseUserModel.isAdmin();
    }
 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("this is granted authorities {}", List.of(new SimpleGrantedAuthority(role.toUpperCase())));
         return List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isEnabled() {
        return baseUserModel.isVerified();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
