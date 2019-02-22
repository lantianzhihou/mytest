package com.wangbo.test.math;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LongRound {

    public static void main(String[] args) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long oldTime = sdf.parse("2018-9-4 23:00:00").getTime();
        long newTime = sdf.parse("2018-9-6 1:00:00").getTime();
        long interval = newTime - oldTime;
        System.out.println(interval);
        System.out.println((interval * 1.0F) / (24 * 60 * 60 * 1000));
        System.out.println(Math.round((interval * 1.0F) / (24 * 60 * 60 * 1000)));
    }
}
