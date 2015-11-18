package me.magicall.time;

/**
 * @author Liang Wenjian
 */
public interface YearConst {
    /**
     * java日历的时间原点的年份
     */
    int START_YEAR = Init.START_YEAR;

    /**
     * java日历中时间原点后的第一个闰年
     */
    int FIRST_LEAP_YEAR = Init.FIRST_LEAP_YEAR;

    /**
     * 闰年2月天数
     */
    int DAYS_COUNT_OF_FEBRUARY_IN_LEAP_YEAR = Init.LEAP_YEAR_FEB_DAYS;
}
