package com.timekeeping.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author minhtq2 on 18/10/2023
 * @project TimeKeeping
 */
@Slf4j
public class TimekeepingAuthenUtil {

    public static String getAPI() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String api = new StringBuilder(request.getMethod() + " " + request.getRemoteAddr() + ":"
                + request.getLocalPort() + request.getRequestURI()).toString();
        return api;
    }
}
