package com.timekeeping.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author minhtq2 on 18/10/2023
 * @project TimeKeeping
 */
@Data
public class UserLoginDto {

    @NotBlank(message = "Username not blank")
    private String username;

    @NotBlank(message = "Password not blank")
    private String password;
}
