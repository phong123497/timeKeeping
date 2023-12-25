package com.timekeeping.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * @author minhtq2 on 25/10/2023
 * @project TimeKeeping
 */
@ResponseStatus(value = HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
public class ProxyAuthenticationRequired extends Exception implements Serializable {

    private static final long serialVersionUID = 1141685067515932795L;

    public ProxyAuthenticationRequired(final String message) {
        super(message);
    }
}