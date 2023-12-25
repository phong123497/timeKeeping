package com.timekeeping.common.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author minhtq2 on 17/10/2023
 * @project TimeKeeping
 */
public class DateUtils {

    static ZoneId defaultZoneId = ZoneId.of("Asia/Tokyo");

    /**
     * @param format String date
     * @return String
     */
    public static String getFormatDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(DateUtils.getDateCurrent());
    }

    public static final Date getDateCurrent() {
        LocalDateTime currentTime = LocalDateTime.now(defaultZoneId);
        return Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now(defaultZoneId));
    }
}
