package com.timekeeping.common.config;

import com.timekeeping.common.constant.ApplicationConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author minhtq2 on 17/10/2023
 * @project TimeKeeping
 */
public class TimeKeepingCustomHttpServletRequest extends HttpServletRequestWrapper {

    private Set<String> headerNameSet;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public TimeKeepingCustomHttpServletRequest(HttpServletRequest request) {
        super(request);

        headerNameSet = new HashSet<>();
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        if (headerNameSet == null) {
            // first time this method is called, cache the wrapped request's header names:
            headerNameSet = new HashSet<>();
            Enumeration<String> wrappedHeaderNames = super.getHeaderNames();
            while (wrappedHeaderNames.hasMoreElements()) {
                String headerName = wrappedHeaderNames.nextElement();
                if (!ApplicationConstant.AUTHENTICATION_SCHEME_NAME.equalsIgnoreCase(headerName)) {
                    headerNameSet.add(headerName);
                }
            }
        }
        return Collections.enumeration(headerNameSet);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (ApplicationConstant.AUTHENTICATION_SCHEME_NAME.equalsIgnoreCase(name)) {
            return Collections.<String>emptyEnumeration();
        }
        return super.getHeaders(name);
    }

    @Override
    public String getHeader(String name) {
        if (ApplicationConstant.AUTHENTICATION_SCHEME_NAME.equalsIgnoreCase(name)) {
            return null;
        }
        return super.getHeader(name);
    }

    public void removeHeader(String name) {
        headerNameSet.remove(name);
    }
}

