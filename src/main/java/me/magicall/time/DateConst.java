package me.magicall.time;

import java.util.Date;
import java.util.List;

/**
 * @author Liang Wenjian
 */
public interface DateConst {

    /**
	 * java long型能表示的最小时间
	 */
    Date START = new Date(Long.MIN_VALUE);
    /**
	 * 0毫秒的时间
	 */
    Date _0 = Init.START_DATE;
    /**
	 * java long型能表示的最大时间
	 */
    Date END = new Date(Long.MAX_VALUE);

	/**
	 * 平年每月天数
	 */
    List<Integer> MONTH_DAYS_OF_COMMON_YEAR = Init.MONTH_DAYS_OF_COMMON_YEAR;
	/**
	 * 闰年每月天数
	 */
    List<Integer> MONTH_DAYS_OF_LEAP_YEAR = Init.MONTH_DAYS_OF_LEAP_YEAR;
}
