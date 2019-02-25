package com.wangbo.test.java8.stream;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

public class StatisticsStream {

    @Test
    public void displayStatistics() {
        List<String> lists = Lists.newArrayList("abc", "", "bc", "efg", "abcd", "", "jkl");
        System.out.println(lists.stream().filter(x -> x != null && !x.isEmpty())
                .collect(Collectors.joining("-", "合并的字符串为：", "!")));
        /**
         * 收集器Collectors将流对象转换成列表或字符串，或者生成统计对象IntSummaryStatistics，此时流对象已经被关闭， 不能再操作
         * java.lang.IllegalStateException: stream has already been operated upon or
         * closed
         */
        Stream<Integer> intStream = lists.stream().filter(x -> x != null && !x.isEmpty()).map(x -> x.length())
                .distinct().map(x -> x * x * x).sorted();
        List<Integer> result = intStream.collect(Collectors.toList());
        IntSummaryStatistics statisticsRes = result.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("列表: " + result);
        System.out.println("列表中最大的数 : " + statisticsRes.getMax());
        System.out.println("列表中最小的数 : " + statisticsRes.getMin());
        System.out.println("所有数之和 : " + statisticsRes.getSum());
        System.out.println("平均数 : " + statisticsRes.getAverage());
    }
}
