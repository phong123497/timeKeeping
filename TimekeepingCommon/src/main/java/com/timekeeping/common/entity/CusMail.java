package com.timekeeping.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CusMail {
    private String[] toMailAddress;

    private String[] toCCAddress;

    private String subjectMail;

    private String contentMail;

    private Map<String, Object> propertiesMail;

}
