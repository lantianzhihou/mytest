package com.wangbo.test.dateTime.java8;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * SimpleDateFormat是线程不安全的，如果在多个线程中使用同一个DateFormat实例格式化时间，会报出异常:
 * 1.使用ThreadLocal为每一个线程创建一个单独的DateFormat实例; 
 * 2.将格式化语句进行同步化，使用synchronized;
 * 3.使用第三方的时间日期包或者java8的LocalDateTime;
 *
 */
public class TestSimpleDateFormat {

    public static void main(String[] args) {
    }

    @Test
    public void testDateFormatException() throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Callable<Date> task = () -> sdf.parse("20170806");

        List<Future<Date>> results = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (Future<Date> future : results) {
            System.out.println(future.get());
        }
    }

    @Test
    public void testThreadLocal() throws ExecutionException, InterruptedException {
        Callable<Date> task = () -> convert("20170806");

        List<Future<Date>> results = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (Future<Date> future : results) {
            System.out.println(future.get());
        }
    }

    @Test
    public void testLocalDateTime() throws ExecutionException, InterruptedException {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Callable<LocalDateTime> task = () -> LocalDateTime.parse("2017-08-06 12:32:51",
                timeFormatter);

        List<Future<LocalDateTime>> results = new ArrayList<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }
        for (Future<LocalDateTime> future : results) {
            System.out.println(future.get());
        }
    }

    private static final ThreadLocal<DateFormat> df = ThreadLocal
            .withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    private Date convert(String source) throws ParseException {
        return df.get().parse(source);
    }
}
