package com.namutech.spero.common.util;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class CommonUtil {

    public static Double convertByteToGigaByte(Long byteValue) {
        return (double) byteValue / (1024 * 1024 * 1024);
    }

    public static Double convertGigaByteToTeraByte(Double gigaByteValue) {
        return gigaByteValue / 1024;
    }

    public static Double convertByteToTeraByte(Long byteValue) {
        return convertByteToTeraByte(byteValue, 100);
    }

    // overload method
    public static Double convertByteToTeraByte(Long byteValue, int round) {
            Double gigaByteValue = convertByteToGigaByte(byteValue);

        return (double) Math.round(gigaByteValue / 1024 * round) / round;
    }

    public static String getVendorFromCloudId(String cloudId) {
        return cloudId.split("-")[1];
    }

    /**
     * 시간을 받아서 Duration을 반환
     * @param startTime 시작 시간
     * @param endTime 종료 시간
     * @return Duration
     */
    public static String getDurationFromTime(LocalTime startTime, LocalTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours() + "시간 " + duration.toMinutes() % 60 + "분";
    }

    /**
     * 두 날짜 사이의 기간을 반환
     * @param startDate 시작 날짜
     * @param endDate 종료 날짜
     * @return 기간
     */
    public static String getPeriodFromDate(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        return period.getYears() + "년 " + period.getMonths() + "개월 " + period.getDays() + "일";
    }

    /**
     * 두 날짜(시간) 사이의 기간을 반환
     * @param startDateTime 시작 날짜(시간)
     * @param endDateTime 종료 날짜(시간)
     * @return 기간(년, 월, 일, 시간, 분, 초)
     */
    public static String getChronoUnitFromDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        long months = ChronoUnit.MONTHS.between(startDateTime, endDateTime);
        long weeks = ChronoUnit.WEEKS.between(startDateTime, endDateTime);
        long days = ChronoUnit.DAYS.between(startDateTime, endDateTime);
        long hours = ChronoUnit.HOURS.between(startDateTime, endDateTime);
        long minutes = ChronoUnit.MINUTES.between(startDateTime, endDateTime);
        long seconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime);

        return months + "개월 " + weeks + "주 " + days + "일 " + hours + "시간 " + minutes + "분 " + seconds + "초";
    }
}
