package com.timekeeping.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

/**
 * @author minhtq2 on 25/10/2023
 * @project TimeKeeping
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ExpiredResourcesDateException extends Exception implements Serializable {


    private static final long serialVersionUID = 2141685067515932795L;

    public ExpiredResourcesDateException(final String message) {
        super(message);
    }
}
