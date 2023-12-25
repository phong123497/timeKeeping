package com.timekeeping.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author minhtq2 on 16/10/2023
 * @project BE
 */
public class ApiWhiteListContextPathConst {
    public static final List<String> authWhiteList = Arrays.asList("/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
            "/account-managers/login", "/account-managers/login-facebook", "/account-managers/login-google",
            "/users/authorization", "/users/login-facebook", "/users/login-google", "/users/login-apple",
            "/users/change-password", "/browser-setting", "/get-file-types");

    public static final String[] authWhiteListArr = {"/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
            "/account-managers/login", "/account-managers/login-facebook", "/account-managers/login-google",
            "/users/authorization", "/users/login-facebook", "/users/login-google", "/users/login-apple",
            "/users/change-password", "/browser-setting", "/get-file-types","/forget/pass"};
}
