package com.wangbo.test.dateTime.java8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.Test;

public class TestPriod {

    @Test
    public void getPeriodYearMonthDay() {
        LocalDate localDate1 = LocalDate.of(2019, 12, 9);
        LocalDate localDate2 = LocalDate.of(2019, 12, 9);
        Period period = Period.between(localDate1, localDate2);
        System.out.println("间隔天数：" + period.get(ChronoUnit.DAYS));
        System.out.println("间隔天数：" + localDate1.until(localDate2, ChronoUnit.DAYS));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime1 = LocalDateTime.parse("2017-08-06 12:32:51", timeFormatter);
        LocalDateTime localDateTime2 = LocalDateTime.parse("2017-08-06 12:32:51", timeFormatter);
        Duration duration = Duration.between(localDateTime1, localDateTime2);
    }
    
    @Test
    public void getPeriodYearMonthDays() {
        System.out.println(Stream.of(1,2,3,4).anyMatch(i -> i > 3));
    }
}
