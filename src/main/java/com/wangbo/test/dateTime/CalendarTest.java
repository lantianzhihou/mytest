package com.wangbo.test.dateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.wangbo.test.util.DateUtil;

public class CalendarTest {

    @Test
    public void firstDay() {
        Calendar calendar = new GregorianCalendar(2018, 11, 30);
        System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(calendar.get(Calendar.DAY_OF_YEAR));
    }

	@Test
	public void firstTest() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		System.out.println("ERA: " + calendar.get(Calendar.ERA));
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
		System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
	}
	
	@Test
	public void addSecondTest() {
		String lastKey = DateUtil.convertToStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.err.println(lastKey);
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(DateUtil.convertToDate(lastKey));
		
		calendar.add(Calendar.SECOND, 1);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void setOneTest() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		calendar.add(Calendar.DAY_OF_YEAR, -30);
		System.out.println("30天前： " + DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		System.out.println("30天前： " + DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void setAllTest() {
		// 2017-11-01 19:07:14
		// 2017-10-31 00:00:00
		// 2017-10-31 23:59:59
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 1, 0, 0,
				0);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
				59);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void weekTest() {
		// 2017-11-01 19:06:47
		// 2017-10-30 00:00:00
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void lastWeekTest() {
		// 2017-11-01 19:04:45
		// 2017-10-29 23:59:59
		// 2017-10-23 00:00:00
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
			calendar.add(Calendar.DAY_OF_YEAR, -1);
		}
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
				59);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

		calendar.add(Calendar.DAY_OF_YEAR, -1);
		calendar.set(Calendar.DAY_OF_WEEK, 2);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}

	@Test
	public void monthTest() {
		System.out.println(DateUtil.explainTimeCondition("last month",DateUtil.convertToDate("2017-11-05 19:04:01")));
		
		// 2017-11-01 19:04:01
		// 2017-10-31 19:04:01
		// 2018-02-28 19:04:01
		// 2018-03-28 19:04:01
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		System.out.println(DateUtil.convertToStr(trialTime, "yyyy-MM-dd HH:mm:ss"));
		calendar.setTime(trialTime);

		calendar.add(Calendar.DAY_OF_YEAR, -1);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

		calendar.add(Calendar.MONTH, 4);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));

		calendar.add(Calendar.MONTH, 1);
		System.out.println(DateUtil.convertToStr(calendar.getTime(), "yyyy-MM-dd HH:mm:ss"));
	}
}
