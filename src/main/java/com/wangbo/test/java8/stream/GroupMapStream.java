package com.wangbo.test.java8.stream;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Collectors类实现了很多归约操作，例如将流转换成集合和聚合元素:
 */
public class GroupMapStream {
    public static void main(String[] args) {

    }

    /**
     * 1.mapping(Function mapper,Collector downstream)
     * 将流中元素通过mapper映射成新的结果,然后收集到指定集合downstream中;
     */
    @Test
    public void testMappingFunction() {
        Set<Integer> set1 = Stream.of(1, 2, 3, 4, 5).collect(Collectors.mapping(Math::negateExact, Collectors.toSet()));
        System.out.println(set1);
    }

    /**
     * 2.groupingBy(Function classifier,Supplier mapFactory, Collector downstream)
     * 将流中元素通过分类器classifier分类, 放入指定的集合downstream中,最后通过map对象mapFactory分类管理;
     */
    @Test
    public void testGroupingByFunction() {
        HashMap<String, List<Integer>> groupMap = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.groupingBy(i -> i < 2 ? "小于2的值" : "不小于2的值", HashMap::new, Collectors.toList()));
        System.out.println(groupMap.entrySet());
    }

    /**
     * 3.toMap(Function keyMapper,Function valueMapper,BinaryOperator
     * mergeFunction,Supplier mapSupplier)
     * 按照指定构造器mapSupplier生成map,将流中元素按照keyMapper生成map的key,按照valueMapper生成map的value,
     * 然后通过mergeFunction将相同key的value进行合并;
     */
    @Test
    public void testToMapFunction() {
        HashMap<String, List<Object>> toMap = Stream.of(1, 2, 3, 4, 5)
                .collect(Collectors.toMap(i -> i < 2 ? "小于2的值的平方" : "不小于2的值的平方",
                        i -> Stream.of(i).collect(Collectors.mapping(j -> j * j, Collectors.toList())),
                        (oldValue, newValue) -> {
                            oldValue.addAll(newValue);
                            return oldValue;
                        }, HashMap::new));
        System.out.println(toMap);
    }
}
