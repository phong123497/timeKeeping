package com.timekeeping.common.exception;

import com.timekeeping.common.constant.MessageCodeConst;
import com.timekeeping.common.util.DateUtils;
import lombok.*;

import java.util.List;

/**
 * @author minhtq2 on 16/10/2023
 * @project timekeeping
 */
@Data
public class ApiError {
    private String errorCode;
    private Long timestamp;
    private String object;
    private String field;
    private String message;
    private List<ApiSubError> fields;

    public ApiError(String message) {
        this();
        this.message = message;
    }

    public ApiError() {
        timestamp = DateUtils.getDateCurrent().getTime();
        errorCode = MessageCodeConst.FAILED;
    }

    public ApiError(String status, String field, String message) {
        this();
        this.errorCode = status;
        this.field = field;
        this.message = message;
    }

    public ApiError(String errorCode, String message) {
        this();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ApiError(String status, String field, List<ApiSubError> fields, String message) {
        this();
        this.errorCode = status;
        this.field = field;
        this.fields = fields == null || fields.size() < 2 ? null : fields;
        this.message = message;
    }

    public interface ApiSubError {
    }

    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    @Getter
    @Setter
    @Builder
    @Data
    public static class ApiSubErrorCommon implements ApiSubError {

        private String object;

        private String field;

        private Object rejectedValue;

        private String apiErrorCode;

        private String message;

        private Boolean limitMaxRecord;
    }

}
