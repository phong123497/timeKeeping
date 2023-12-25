package com.timekeeping.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author minhtq2 on 10/10/2023
 * @project timekeeping-common
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @JsonProperty(value = "id")
    private String emmployeeId;

    @JsonProperty(value = "username")
    private String username;

    @JsonProperty(value = "role")
    private String role;

}
