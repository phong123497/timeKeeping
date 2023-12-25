package com.timekeeping.common.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.timekeeping.common.dto.UserDto;
import lombok.*;

/**
 * @author minhtq2 on 10/10/2023
 * @project timekeeping-common
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    @JsonProperty(value = "token")
    private String accessToken;

    @JsonProperty(value = "user")
    private UserDto user;
}
