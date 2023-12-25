package com.timekeeping.common.util;

import com.timekeeping.common.auth.CustomUserDetails;
import com.timekeeping.common.constant.MessageCodeConst;
import com.timekeeping.common.entity.UserEntity;
import com.timekeeping.common.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author minhtq2 on 24/10/2023
 * @project TimeKeeping
 */
@Slf4j
public class AuthenUtil {

    public static String getCurrentUserName() throws CommonException {
        String username = "";
        UserEntity user = AuthenUtil.getCurrentUser();
        if (user != null) {
            username = user.getUsername();
        }
        return username;
    }

    public static UserEntity getCurrentUser() throws CommonException {
        try {
            return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserEntity();
        } catch (Exception e) {
            log.error("=== ERROR get current user: " + ExceptionUtils.getStackTrace(e));
            throw new CommonException().setErrorCode(MessageCodeConst.LOGIN_EXPIRED).setStatusCode(HttpStatus.UNAUTHORIZED);
        }
    }

    public static CustomUserDetails getPrincipal() throws CommonException {
        try {
            return ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        } catch (Exception e) {
            throw new CommonException().setErrorCode(MessageCodeConst.LOGIN_EXPIRED).setStatusCode(HttpStatus.UNAUTHORIZED);
        }

    }
}
