package com.wangbo.test.dateTime.java8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

/**
 * <一句话功能简述>
 * 1.Period.between(1, 2)针对的是两个LocalDate之间的间隔，显示的格式为'P{#year}Y{#month}M#{day}D';
 * 2.Period可以获取两个时间间隔的年、月、日计量数值，或者将年月都换成月份计算period.toTotalMonths()
 * 3.Duration.between(1, 2)针对的是两个LocalDateTime之间的间隔，显示的格式为'PT{#hour}H{#minute}M#{second}S';
 * 4.Duration可以获取两个时间间隔的分别以日、时、分、秒、微秒、纳秒为计量单位的总间隔时间
 * @author wangbo
 *
 */
public class TestPriod {

    @Test
    public void getPeriodYearMonthDay() {
        System.out.println("======================使用Period求时间间隔=======================");
        LocalDate localDate1 = LocalDate.of(2018, 2, 24);
        LocalDate localDate2 = LocalDate.of(2020, 4, 23);
        System.out.println("间隔总天数：" + localDate1.until(localDate2, ChronoUnit.DAYS));
        System.out.println("间隔总月数：" + localDate1.until(localDate2, ChronoUnit.MONTHS));
        
        Period period = Period.between(localDate1, localDate2);
        Period period2 = localDate1.until(localDate2);
        System.out.println(period2);     // P2Y1M30D
        System.out.println("间隔总月数：" + period.toTotalMonths());
        System.out.println("两个时间间隔为：" + period.get(ChronoUnit.YEARS) + "年"
                + period.get(ChronoUnit.MONTHS) + "月" + period.get(ChronoUnit.DAYS) + "日");
        LocalDate localDate3 = LocalDate.of(2020, 3, 24);
        System.out.println("间隔总天数：" + localDate3.until(localDate2, ChronoUnit.DAYS));
        
        System.out.println("======================使用Duration求时间间隔=======================");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2016-05-06 12:32:50", timeFormatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse("2017-08-06 12:31:51", timeFormatter);
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        System.out.println(duration);   // PT10967H59M1S
        System.out.println("间隔天数：" + duration.toDays());
        System.out.println("间隔小时数：" + duration.toHours());
        System.out.println("间隔分钟数：" + duration.toMinutes());
        System.out.println("间隔秒数：" + duration.get(ChronoUnit.SECONDS));
        System.out.println("间隔微秒数：" + duration.toMillis());
    }
}
