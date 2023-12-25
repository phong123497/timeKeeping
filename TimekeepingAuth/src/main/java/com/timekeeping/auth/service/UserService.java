package com.timekeeping.auth.service;

import com.timekeeping.common.auth.JwtAuthenticationResponse;
import com.timekeeping.common.dto.UserLoginDto;

public interface UserService {
    JwtAuthenticationResponse login(UserLoginDto userLoginDto) throws Exception;
}
