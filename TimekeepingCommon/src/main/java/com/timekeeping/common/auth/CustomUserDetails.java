package com.timekeeping.common.auth;

import com.timekeeping.common.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author minhtq2 on 10/10/2023
 * @project timekeeping-common
 */
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
@Transactional
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;
    private String session;
    @Builder.Default
    private int jwtTtlInMinutes = 0;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userEntity != null && userEntity.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(userEntity.getRole()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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

    @Override
    public boolean isEnabled() {
        return true;
    }

    public CustomUserDetails(UserEntity user) {
        super();
        this.userEntity = user;
    }
}