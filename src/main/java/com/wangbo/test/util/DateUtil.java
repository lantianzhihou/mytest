package com.wangbo.test.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {

	private static List<String> types = Arrays.asList("yyyy-MM-dd HH:mm:ss","yyyy-MM-dd");

	public static Date convertToDate(String strTime) {
		DateFormat dateFormat = null;
		for (String dateType : types) {
			dateFormat = new SimpleDateFormat(dateType);
			try {
				return dateFormat.parse(strTime);
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public static String convertToStr(Date date, String dateType) {
		if (types.contains(dateType)) {
			DateFormat dateFormat = new SimpleDateFormat(dateType);
			return dateFormat.format(date);
		}
		return null;
	}

	public static Map<String, Date> explainTimeCondition(String timeCondition,Date nowTime) {
		Map<String, Date> timeMap = new HashMap<>();

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(nowTime);
		switch (timeCondition) {
		case "today":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0,
					0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "yesterday":
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 1, 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
					59);
			timeMap.put("endTime", calendar.getTime());
			break;
		case "before yesterday":
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 2, 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
					59);
			timeMap.put("endTime", calendar.getTime());
			break;
		case "2d":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 1, 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "3d":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 2, 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "this week":
			timeMap.put("endTime", calendar.getTime());
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}
			calendar.set(Calendar.DAY_OF_WEEK, 2);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0,
					0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "last week":
			if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
				calendar.add(Calendar.DAY_OF_YEAR, -1);
			}
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59,
					59);
			timeMap.put("endTime", calendar.getTime());
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			calendar.set(Calendar.DAY_OF_WEEK, 2);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0,
					0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "last month":
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0,
					0);
			timeMap.put("endTime", calendar.getTime());
			calendar.add(Calendar.MONTH, -1);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "15d":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE) - 15, 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "1m":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DATE), 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "2m":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 2, calendar.get(Calendar.DATE), 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		case "3m":
			timeMap.put("endTime", calendar.getTime());
			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 3, calendar.get(Calendar.DATE), 0,
					0, 0);
			timeMap.put("startTime", calendar.getTime());
			break;
		default:
			break;
		}
		return timeMap;
	}
}
