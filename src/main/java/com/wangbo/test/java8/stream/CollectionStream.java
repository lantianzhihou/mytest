package com.wangbo.test.java8.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.Lists;

public class CollectionStream {
    public static void main(String[] args) {

    }

    /**
     * Collectors类实现了很多归约操作，例如将流转换成集合和聚合元素:
     * 1.toCollection\toSet指定集合构造器,将流中元素转成集合中的元素，并返回集合;
     * 2.joining指定分割符、前缀、后缀将流中的字符元素拼接成字符串; 3.minBy指定比较器返回流中最小值或者最大值;
     * 4.reducing指定容器初始值identity、一元运算mapper、二元运算BinaryOperator将流中元素分解操作，
     * 先进行一元操作，取结果与容器值进行二元操作，然后将结果放入容器中，最后返回容器值;
     * 5.partitioningBy通过指定的判断器，将流中元素分离成两组（true和false）,每组元素放入指定的集合Collector中;
     * 6.summingInt 将流中元素按照ToIntFunction的算法转换成int类型结果,然后累计求和;
     * 7.averagingInt将流中元素按照ToIntFunction的算法转换成int类型结果,然后累计求平均值;
     * 8.Collectors.summarizingInt根据算法将流中元素指定项进行统计;
     */
    @SuppressWarnings("unused")
    @Test
    public void testCollectorsFunction() {
        Set<Integer> set1 = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toCollection(TreeSet::new));
        Set<Integer> set2 = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toSet());
        List<Integer> list = Stream.of(1, 2, 3, 4, 5).collect(Collectors.toList());
        String collect = Stream.of(1, 2, 3, 4, 5).map(i -> String.valueOf(i))
                .collect(Collectors.joining(",", "前缀", "后缀"));
        Integer min = Stream.of(1, 2, 3, 4, 5).collect(Collectors.minBy((a, b) -> a - b)).get();
        Integer sum = Stream.of(1, 2, 3, 4, 5).collect(Collectors.reducing(1, t -> t * t, (a, mt) -> a + mt));
        Map<Boolean, Set<Integer>> groupByPredicate = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.partitioningBy(i -> i >= 3, Collectors.toSet()));
        System.out.println(groupByPredicate.entrySet());
    }

    /**
     * 提供类似SQL语句一样的操作，比如distinct,filter, map, reduce, find, match, sorted,limit等 :
     * 1.distinct 根据流中元素的equals方法，在流中进行去重; 2.filter 根据指定过滤法则Predicate对流中元素进行过滤;
     * 3.map 将流中元素遍历使用指定的算法生成新的元素流 ; 4.flatMap 将流中元素遍历根据指定算法生成新的子元素流，然后子元素流合并后返回新流;
     * 5.reduce 在指定初始化值时将流中元素遍历执行累计操作BiFunction; 6.sorted 将流中元素按照指定的排序规则重新排序;;
     * 7.peek 将流中元素遍历操作，但该方法不会立刻执行，而是在stream执行消费操作后再执行，通常用于日志;
     * 8.limit将流中元素按照顺序只取指定数量的size的元素; 9.skip 跳过流中排在前面指定数量的元素，返回后面的元素流;
     * 10.toArray指定构造函数，返回包含流中所有元素的数组;
     * 11.collect(collector)根据指定的收集器将流中元素按照accumulator进行计算后收集到supplier中然后返回;
     * 12.min\max通过指定的比较器返回流中的最小值\最大值;count统计流中元素的个数
     * 13.IntStream.summaryStatistics可以获取number类型元素流的统计数据
     */
    @Test
    public void testCommonFunction() {
        ArrayList<Integer> lists = Lists.newArrayList(5, 6, 9, 11, 1, 3, 2, 3, 4);
        lists.parallelStream().sequential().map(i -> i * i).forEach(System.out::println);
        lists.stream().flatMap(i -> Stream.of(i)).forEach(System.out::println);
        System.out.println("=====================================================");
        /*
         * lists.stream().skip(3).forEach(System.out::println); Integer[] array =
         */
        /**
         * toArray方法的底层核心方法实现 ==>
         * this.array = generator.apply((int)exactOutputSizeIfKnown(spliterator));
         */
        Integer[] array = lists.stream().filter(x -> 5 < x && x < 11).toArray(Integer[]::new);
        lists.stream().filter(x -> 1 < x && x < 11).sorted((a, b) -> b - a)
                .peek(e -> System.out.println("Sorted value: " + e)).parallel().map(i -> i * i).distinct().limit(4)
                .collect(Collectors.toList()).forEach(System.out::println);

    }

    /**
     * 1.parallel()获取一个对应的并行流；
     * 2.并行流的forEach输出的顺序不一定（效率更高），而forEachOrdered输出的顺序与集合中元素顺序一致
     * 3.parallel()针对输出的无序性只对forEach方法有效,并行流的初始目的只是为了提供执行效率 4.sequential()将一个流转换成串行流
     */
    @Test
    public void testForEach() {
        List<String> names = Arrays.asList("照桌", "没改", "阿布", "唐山", "厕所");
        names.stream().forEach(name -> System.out.println(name));
        System.out.println("=====================================================");
        names.parallelStream().forEach(name -> System.out.println(name));
        System.out.println("=====================================================");
        names.stream().parallel().forEachOrdered(name -> System.out.println(name));
    }

    /**
     * Stream（流）的来源，可以是集合，数组，I/O channel， 产生器generator 等 聚合操作 类似SQL语句一样的操作，
     * 创建的方法：StreamSupport.stream(spliterator(), false)
     * 1.通过collection.stream()获取集合的流； 2.通过Random对象的ints等方法获取数字流；
     * 3.通过stream中的builder对象添加元素，然后生成相应的流； 4.通过Stream.of(t)生成唯一元素流；
     * 5.通过Arrays.stream或者Stream.of(T... t)生成多元素流；
     * 6.通过Stream.iterate和Stream.generate生成满足指定条件的无限元素流；
     * 7.通过BufferedReader.lines()和Files.lines(path, cs)将指定文件的IO流读取到string流中；
     * 
     * @throws IOException
     */
    @SuppressWarnings("all")
    @Test
    public void testBuildStream() throws IOException {
        Stream<String> stream1 = new ArrayList<String>().stream();

        IntStream intStream = new Random().ints(20, 5, 15);

        Stream<Object> stream3 = Stream.builder().add(123).add(2).build();

        Stream<String> stream4 = Stream.of("唯一元素流");

        Stream<String> stream5 = Arrays.stream(new String[] { "多元素流1", "多元素流2" });
        Stream<String> stream6 = Stream.of("多元素流1", "多元素流2");

        Stream<Integer> stream7 = Stream.iterate(2, i -> i++);

        Random random = new Random();
        Stream<Integer> stream8 = Stream.generate(() -> random.nextInt(50));

        Stream<String> stream9 = new BufferedReader(new FileReader("C:/ceshi/haha.txt")).lines();
        Stream<String> stream10 = Files.lines(new File("C:/ceshi/haha.txt").toPath(), StandardCharsets.UTF_8);
    }

    @Test
    public void displayRandom() {
        // 输出10个随机数
        Random random = new Random();
        List<Integer> result = random.ints(-200, 200).filter(x -> x > -120 && x < 180).limit(10).sorted()
                .mapToObj(x -> x).collect(Collectors.toList());
        System.out.println(result);
    }
    
    @Test
    public void testInterupt() {
        Stream.of(1,2,3,4).forEach(i -> {
            System.out.println(i / 0);
        });
        System.out.println(123);
    }

}
