package com.timekeeping.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * @author minhtq2 on 23/10/2023
 * @project TimeKeeping
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDTO{
    @JsonProperty(value = "billing")
    private Page<?> users;
}
