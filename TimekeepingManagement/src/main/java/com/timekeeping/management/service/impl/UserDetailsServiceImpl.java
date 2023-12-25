package com.timekeeping.management.service.impl;

import com.timekeeping.common.auth.CustomUserDetails;
import com.timekeeping.common.constant.MessageCodeConst;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author minhtq2 on 23/10/2023
 * @project TimeKeeping
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userSearch = new UserEntity(username);
        UserEntity userEntity = userRepository.getUserEntityByUsername(userSearch.getUsername());
        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException(MessageCodeConst.USER_INVALID);
        }
        return new CustomUserDetails(userEntity);
    }
}
