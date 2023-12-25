package com.timekeeping.auth.exception.handle;

import com.timekeeping.common.component.ResponseDataConfiguration;
import com.timekeeping.common.constant.ResponseStatusConst;
import com.timekeeping.common.exception.*;
import com.timekeeping.common.util.StringUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author minhtq2 on 24/10/2023
 * @project TimeKeeping
 */
@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    /**
     * Exception unspecified HttpStatus
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(CommonException.class)
//    public ResponseEntity<ResponseData<?>> handleAllException(CommonException ex, WebRequest request) {
    public ResponseEntity<ApiError> handleAllException(CommonException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getErrorCode(), ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, ex.getStatusCode());
    }

    /**
     * BAD_REQUEST 400
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                    WebRequest request) {
        List<String> errors = new ArrayList<>();
        List<ApiError.ApiSubError> fields = new ArrayList<>();
        String field = null, object = null;
        Object rejectedValue = null;
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .sorted((o1, o2) -> o1.getField().compareTo(o2.getField())).collect(Collectors.toList());
        for (FieldError error : fieldErrors) {
            if (field != null && !field.equals(error.getField())) {
                fields.add(ApiError.ApiSubErrorCommon.builder().field(field).message(lstToStr(errors))
                        .rejectedValue(rejectedValue).object(object).build());
                errors = new ArrayList<>();
            }
            errors.add(error.getDefaultMessage());
            field = error.getField();
            object = error.getObjectName();
            rejectedValue = error.getRejectedValue();
        }
        fields.add(ApiError.ApiSubErrorCommon.builder().field(field).message(lstToStr(errors)).rejectedValue(rejectedValue)
                .object(object).build());
        ApiError apiError = new ApiError("400", field, fields, lstToStr(errors));
        apiError.setObject(object);
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, apiError, HttpStatus.BAD_REQUEST);
    }

    private String lstToStr(List<String> list) {
        if (!StringUtil.isTrue(list))
            return "";
        StringBuilder buffer = new StringBuilder();
        boolean frist = true;
        for (String str : list) {
            if (!frist)
                buffer.append(", ");
            buffer.append("[").append(str).append("]");
            frist = false;
        }
        return buffer.toString();
    }

    /**
     * BAD_REQUEST 400
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({MissingRequestHeaderException.class, BadRequestException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> headMissException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.BAD_REQUEST);
    }

    /**
     * UNAUTHORIZED 401
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiError> loginFailedException(UnauthorizedException ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * FORBIDDEN 403
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ExpiredResourcesDateException.class, PermissionException.class, IllegalArgumentException.class,
            SignatureException.class, MalformedJwtException.class, UnsupportedJwtException.class,
            ExpiredJwtException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ResponseEntity<ApiError> permissionException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.FORBIDDEN);
    }

    /**
     * NOT_FOUND 404
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiError> handleNoMethodException(WebRequest request, NoHandlerFoundException ex) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.NOT_FOUND);
    }

    /**
     * UNAUTHORIZED 407
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ProxyAuthenticationRequired.class})
    @ResponseStatus(value = HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
    public ResponseEntity<ApiError> tokenFailedException(ProxyAuthenticationRequired ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.PROXY_AUTHENTICATION_REQUIRED);
    }

    /**
     * CONFLICT 409
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ResponseEntity<ApiError> resourceNotFoundException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.CONFLICT);
    }

    /**
     * Throwable 500
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleDefaultException(WebRequest request, Throwable ex) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * INTERNAL_SERVER_ERROR 500
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * NOT_IMPLEMENTED 501
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler({NotAllowCallingException.class, FileIOException.class})
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ResponseEntity<ApiError> notImplementedException(Exception ex, WebRequest request) {
        ApiError error = new ApiError(ex.getMessage());
        return ResponseDataConfiguration.error(ResponseStatusConst.ERROR, error, HttpStatus.NOT_IMPLEMENTED);
    }
}
