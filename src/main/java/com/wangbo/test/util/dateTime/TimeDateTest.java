package com.wangbo.test.util.dateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TimeDateTest implements Runnable{
	public static long validTime = 3*30*24*60*60*1000L;
	
	@Test
	public void firstTest() {
        StringBuilder sb = new StringBuilder();
        sb.append(1).append(2);
        System.out.println(sb.length());
        sb.replace(0, sb.length(), "");
        System.out.println(sb.length());
//		String num = "01";
//		System.out.println(Integer.parseInt(num));
        // System.out.println(new Date(1536288204213L));
//		long currentTimeMillis = System.currentTimeMillis();
//		System.out.println(currentTimeMillis);
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(dateFormat.format(new Date(currentTimeMillis)));
//		System.out.println(dateFormat.format(new Date(currentTimeMillis + validTime)));
		
        // System.out.println(Runnable.class.isAssignableFrom(TimeDateTest.class));
	} 
	
	@Test
	public void secondTest() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis);
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		System.out.println(dateFormat.format(new Date(currentTimeMillis)));
		
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(currentTimeMillis);
		
		calendar.add(Calendar.HOUR_OF_DAY, -9);
		System.out.println(dateFormat.format(calendar.getTime()));
		
		Date date1 = dateFormat.parse("15:53");
		System.out.println(date1.getTime());
		Date date2 = dateFormat.parse("08:00");
		System.out.println(date2.getTime());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	} 
}
