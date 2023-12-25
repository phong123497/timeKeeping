package com.timekeeping.common.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minhtq2 on 16/10/2023
 * @project timekeeping
 */
@Getter
public class CommonException extends Exception {

    private static final long serialVersionUID = -3258098393078013278L;
    private String message;
    private String errorCode;
    private String object;
    private String field;
    private HttpStatus statusCode = HttpStatus.OK;
    private List<Object> placeholder = new ArrayList<>();

    public CommonException () {
        super();
    }

    public CommonException setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CommonException setField(String field) {
        this.field = field;
        return this;
    }

    public CommonException setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public CommonException addPlaceholder(Object placeholder) {
        if (this.placeholder == null) {
            this.placeholder = new ArrayList<>();
        }
        this.placeholder.add(placeholder);
        return this;
    }
}
