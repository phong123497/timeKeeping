package com.timekeeping.common.constant;

/**
 * @author minhtq2 on 16/10/2023
 * @project TimeKeeping
 */
public class ApplicationConstant {
    public static final String AUTHENTICATION_SCHEME_NAME = "Authorization";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String TOKEN_SCHEME_NAME = "Bearer ";

    public static final String TOKEN_BASIC = "Basic ";

    public interface ContextPath {
        String AUTH_CONTEXT_PATH = "/api/auth";
        String MANAGEMENT_CONTEXT_PATH = "/api/management";
    }

    public interface REDIS {
        String NAME_GROUP_USER_TOKEN = "user_token";
        String KEY_START_COUNT_INPUT_REMAINING = "user_input_remaining_";
        String SUFFIXES_TOKEN = "_token";
        String PREFIXES_GROUP_USER = "user_";
        String LOCK_USER = "lock_user";
        String USER_SESSION = "user_session";
        int jwtTtlInMinutes = 365 * 24 * 60;
        int LIMIT_DEVICE_CURRENT = 2;
        Long MAX_DEVICE = 4L;
    }
}
