package com.timekeeping.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * @author minhtq2 on 25/10/2023
 * @project TimeKeeping
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Slf4j
public class ResourceNotFoundException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, final Exception ex) {
        super(message);
        ResourceNotFoundException.log.error(message, ex);
    }
}
