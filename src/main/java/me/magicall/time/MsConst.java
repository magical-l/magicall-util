package me.magicall.time;

import java.util.concurrent.TimeUnit;

/**
 * @author Liang Wenjian
 */
public interface MsConst {

    long SECOND = TimeUnit.SECONDS.toMillis(1);
    long MINUTE = TimeUnit.MINUTES.toMillis(1);
    long HOUR = TimeUnit.HOURS.toMillis(1);
    /**
     * 8小时的毫秒数.用于时区计算.中国是+8时区.
     */
    long HOUR8 = TimeUnit.HOURS.toMillis(8);
    /**
     * 16小时的毫秒数.用于时区计算.中国是+8时区,对24的补数是16.
     */
    long HOUR16 = TimeUnit.HOURS.toMillis(16);
    long DATE = TimeUnit.DAYS.toMillis(1);
    long DAY = DATE;
    long WEEK = TimeUnit.DAYS.toMillis(7);
    long DAY3 = TimeUnit.DAYS.toMillis(3);
    long DAY7 = WEEK;
    long DAY28 = TimeUnit.DAYS.toMillis(28);
    long DAY29 = TimeUnit.DAYS.toMillis(29);
    long DAY30 = TimeUnit.DAYS.toMillis(30);
    long DAY31 = TimeUnit.DAYS.toMillis(31);
    long DAY365 = TimeUnit.DAYS.toMillis(365);
    long DAY366 = TimeUnit.DAYS.toMillis(366);
    long YEAR_COMMON = DAY365;
    long YEAR_LEAP = DAY366;

    /**
     * 普通连续4年的毫秒总数，注意其中有一年是闰年
     */
    long WITH_LEAP_4YEAR = YEAR_COMMON * 3 + YEAR_LEAP;

    /**
     * 不带有闰年的4年毫秒总数
     */
    long WITHOUT_LEAP_4YEAR = YEAR_COMMON * 4;
}
