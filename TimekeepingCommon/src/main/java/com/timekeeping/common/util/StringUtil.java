package com.timekeeping.common.util;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

/**
 * @author minhtq2 on 17/10/2023
 * @project TimeKeeping
 */
public class StringUtil {

    /**
     * Determines if a string is not empty.
     */
    public static boolean isNotEmpty(final CharSequence string) {
        return string != null && string.length() > 0;
    }

    public static boolean isTrue(Object value) {

        if (value == null) {
            return false;
        }

        if (value instanceof String) {
            return !((String) value).trim().isEmpty();
        }

        if (value instanceof StringBuffer) {
            return !((StringBuffer) value).toString().trim().isEmpty();
        }

        if (value instanceof StringBuilder) {
            return !((StringBuilder) value).toString().trim().isEmpty();
        }

        if (value instanceof Number) {
//            return !((Number) value).equals(Long.valueOf(0));
            return ((Number) value).longValue() >= 0;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        if (value instanceof Collection) {
            return !((Collection<?>) value).isEmpty();
        }

        if (value instanceof List) {
            return !((List<?>) value).isEmpty();
        }

        if (value instanceof Page) {
            return !((Page<?>) value).isEmpty();
        }

        if (value instanceof Object[]) {
            return ((Object[]) value).length > 0;
        }

        if (value instanceof XSSFCell) {
            if (value != null) {
                if (value.toString().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
}
