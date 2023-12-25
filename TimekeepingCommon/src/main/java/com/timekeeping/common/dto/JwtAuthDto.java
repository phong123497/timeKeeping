package com.timekeeping.common.dto;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author minhtq2 on 16/10/2023
 * @project TimeKeeping
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthDto {
    private String userName;
    private String role;
    private Timestamp expi;
    private Timestamp expireResource;
    private String ss;
    private String issuer;

}
