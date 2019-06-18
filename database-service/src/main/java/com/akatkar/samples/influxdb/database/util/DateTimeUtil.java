package com.akatkar.samples.influxdb.database.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneOffset;

public final class DateTimeUtil {

    private DateTimeUtil() {
        // prevent instance
    }

    public static String formattedDuration(long miliseconds) {

        Duration duration = Duration.ofMillis(miliseconds);

        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration
                .minusHours(hours)
                .minusMinutes(minutes)
                .getSeconds();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static Long getDaysBefore(long days) {
        return LocalDate.now()
                .minusDays(days)
                .atStartOfDay()
                .toEpochSecond(ZoneOffset.UTC);
    }
}
