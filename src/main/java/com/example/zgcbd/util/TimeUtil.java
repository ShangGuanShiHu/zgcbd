package com.example.zgcbd.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {
    public static Timestamp transTimeToLocal(Timestamp t){
        // 将Timestamp转换为LocalDateTime
        LocalDateTime localDateTime = t.toLocalDateTime();

        // 将LocalDateTime转换为ZonedDateTime，并增加8小时
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault()).plusHours(8);

        // 将ZonedDateTime转换回Timestamp
        t = Timestamp.valueOf(zonedDateTime.toLocalDateTime());

        return t;
    }
}
