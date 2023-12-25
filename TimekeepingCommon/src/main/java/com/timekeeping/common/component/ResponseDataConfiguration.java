package com.timekeeping.common.component;

import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * @author minhtq2 on 16/10/2023
 * @project timekeeping
 */
public class ResponseDataConfiguration implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> ResponseEntity<T> successDefault(T body) {
        return new ResponseEntity(body, HttpStatus.OK);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> ResponseEntity<T> error(Integer status, ApiError error, HttpStatus httpStatus) {
        ResponseData<T> responseData = new ResponseData<T>(status, error);
        return new ResponseEntity(responseData, httpStatus);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> ResponseEntity<T> success(T body) {
        ResponseData<T> responseData = new ResponseData<T>(ResponseStatusConst.SUCCESS, body);
        return new ResponseEntity(responseData, HttpStatus.OK);
    }
}
