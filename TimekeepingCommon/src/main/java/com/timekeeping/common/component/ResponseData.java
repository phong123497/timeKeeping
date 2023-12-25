package com.timekeeping.common.component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.timekeeping.common.exception.ApiError;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author minhtq2 on 16/10/2023
 * @project TimeKeeping
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@JsonInclude
public class ResponseData <T> implements Serializable {

    private Integer status;

    private ApiError error;

    private T body;

    public ResponseData(Integer status, T body) {
        this.status = status;
        this.error = null;
        this.body = body;
    }

    public ResponseData(Integer status, ApiError error) {
        this.status = status;
        this.error = error;
        this.body = null;
    }

    public ResponseData(Integer status, List<ApiError> errors) {
        this.status = status;
        this.body = null;
        this.error = null;
    }

}

