package me.magicall.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Liang Wenjian
 */
class Init {

    static final Date START_DATE = new Date(0);
    static final int LEAP_YEAR_FEB_DAYS = 29;
    static final int START_YEAR;
    static final int FIRST_LEAP_YEAR;
    static {
        {
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(START_DATE);
            final int startYear = calendar.get(Calendar.YEAR);
            final int firstLeapYear;
            for (int i = startYear; true; ++i) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, Calendar.MARCH);
                calendar.set(Calendar.DATE, 1);
                calendar.add(Calendar.DATE, -1);
                final int date = calendar.get(Calendar.DATE);
                if (date == LEAP_YEAR_FEB_DAYS) {
                    firstLeapYear = calendar.get(Calendar.YEAR);
                    break;
                }
            }
            START_YEAR = startYear;
            FIRST_LEAP_YEAR = firstLeapYear;
        }
    }

    private static final int[] month_days_of_common_year = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    static final List<Integer> MONTH_DAYS_OF_COMMON_YEAR;
    static {
        final List<Integer> l1 = new ArrayList<>(month_days_of_common_year.length);
        for (final int i : month_days_of_common_year) {
            l1.add(i);
        }
        MONTH_DAYS_OF_COMMON_YEAR = Collections.unmodifiableList(l1);
    }

    private static final int[] month_days_of_leap_year = { 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    static final List<Integer> MONTH_DAYS_OF_LEAP_YEAR;
    static {
        final List<Integer> l2 = new ArrayList<>(month_days_of_leap_year.length);
        for (final int i : month_days_of_leap_year) {
            l2.add(i);
        }
        MONTH_DAYS_OF_LEAP_YEAR = Collections.unmodifiableList(l2);
    }

    public static void main(final String... args) {
        System.out.println(START_YEAR);
        System.out.println(FIRST_LEAP_YEAR);
        System.out.println(START_DATE);
    }

    private Init() {
        super();
    }
}
