package gw.apiserver.common.utils.date;

import java.time.LocalDate;

public class CommonDateUtil {

    /**
     * 시작일자와 종료일자를 포함하여 기준 일자를 검색
     *
     * 포함될 시 true 아닐 시 false
     * @param date LocalDate 기준 일자
     * @param startDate LocalDate 시작 일자
     * @param endDate LocalDate 종료 일자
     * @return boolean
     */
    public static boolean isWithinDateRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
