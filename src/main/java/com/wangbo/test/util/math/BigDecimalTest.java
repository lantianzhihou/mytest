package com.wangbo.test.util.math;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BigDecimalTest {

    public static void main(String[] args) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str1 = "2018-10-7 00:00:00";
        String str2 = "2018-10-10 00:00:00";
        BigDecimal time1 = new BigDecimal(sdf.parse(str1).getTime());
        System.out.println(time1);
        BigDecimal time2 = new BigDecimal(sdf.parse(str2).getTime());
        System.out.println(time2);
        BigDecimal nowTime = new BigDecimal(System.currentTimeMillis());
        System.out.println(nowTime);
        BigDecimal qian = nowTime.subtract(time1).multiply(new BigDecimal(100));
        BigDecimal hou = time2.subtract(time1);
        System.out.println(nowTime.subtract(time1));
        System.out.println(qian + "===" + hou);
        BigDecimal result = qian.divide(time2.subtract(time1), 3, BigDecimal.ROUND_HALF_UP);

        System.out.println(result.doubleValue());
        System.out.println(result.setScale(1, BigDecimal.ROUND_HALF_UP));
    }

}
