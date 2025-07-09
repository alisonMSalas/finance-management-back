package uta.ec.finance_manager.util;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@Service
public class DateUtil {
    public Date getStartOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        return toDate(startOfWeek.atStartOfDay());
    }

    public Date getEndOfWeek() {
        LocalDate today = LocalDate.now();
        LocalDate endOfWeek = today.with(DayOfWeek.SUNDAY);
        return toDate(endOfWeek.atTime(LocalTime.MAX));
    }

    public Date getStartOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        return toDate(startOfMonth.atStartOfDay());
    }

    public Date getEndOfMonth() {
        LocalDate today = LocalDate.now();
        LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());
        return toDate(endOfMonth.atTime(LocalTime.MAX));
    }

    public Date getStartOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.with(TemporalAdjusters.firstDayOfYear());
        return toDate(startOfYear.atStartOfDay());
    }

    public Date getEndOfYear() {
        LocalDate today = LocalDate.now();
        LocalDate endOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        return toDate(endOfYear.atTime(LocalTime.MAX));
    }

    private Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
