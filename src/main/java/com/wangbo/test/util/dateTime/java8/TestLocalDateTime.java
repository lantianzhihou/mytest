package com.wangbo.test.util.dateTime.java8;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Test;

import com.wangbo.test.util.HanyuPinyinComparator;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class TestLocalDateTime {
    
    public static void main(String[] args) {
        LocalDateTime endTime = LocalDateTime.of(2019, 5, 29, 0, 0, 0);
        LocalDateTime startTime = endTime.minusMonths(3);
        System.out.println(startTime);
    }

    @Test
    public void testChina() {
        List<String> cityInfos = new ArrayList<>();
        cityInfos.add("同兴村");
        cityInfos.add("宗s黄村");
        cityInfos.add("流芳社区");
        cityInfos.add("流芳f啊区");
        cityInfos.add("a大谭村");
        cityInfos.add("牌楼舒村");
        cityInfos.add("泉港村");
        cityInfos.add("求g爱村");
        cityInfos.add("清想村");
        cityInfos.add("邬2家山村");
        Collections.sort(cityInfos,
                (inputStr1, inputStr2) -> HanyuPinyinComparator.compareHanyuPinyinFirstChar(inputStr1, inputStr2));
        System.out.println(cityInfos);
        // System.out.println(sortByHanyuPinyin(cityInfos));
    }

    public static List<String> sortByHanyuPinyin(Collection<String> inputStrs) {
        List<Map<String, String>> comparatorList = new ArrayList<>();

        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        inputStrs.stream().forEach(inputStr -> {
            StringBuilder sb = new StringBuilder();
            for (char cha : inputStr.toCharArray()) {
                if (Pattern.matches("[\u4e00-\u9fa5]", String.valueOf(cha))) {
                    try {
                        sb.append(PinyinHelper.toHanyuPinyinStringArray(cha, defaultFormat)[0].charAt(0));
                    } catch (BadHanyuPinyinOutputFormatCombination e) {
                        e.printStackTrace();
                    }
                } else {
                    sb.append(cha);
                }
            }
            Map<String, String> comparatorMap = new HashMap<>();
            comparatorMap.put("originStr", inputStr);
            comparatorMap.put("pingyinStr", sb.toString());
            comparatorList.add(comparatorMap);
        });
        Collections.sort(comparatorList, (map1, map2) -> map1.get("pingyinStr").compareTo(map2.get("pingyinStr")));
        System.out.println("字符串集合的首字母拼音："
                + comparatorList.stream().map(strMap -> strMap.get("pingyinStr")).collect(Collectors.toList()));
        return comparatorList.stream().map(strMap -> strMap.get("originStr")).collect(Collectors.toList());
    }

    @Test
    public void testZonedDateTime() {
        // 获取当前时间日期
        ZonedDateTime date1 = ZonedDateTime.parse("2015-12-03T10:15:30+05:30[Asia/Shanghai]");
        System.out.println("date1: " + date1);

        ZoneId id = ZoneId.of("Europe/Paris");
        System.out.println("ZoneId: " + id);

        ZoneId currentZone = ZoneId.systemDefault();
        System.out.println("当期时区: " + currentZone);
    }

    /**
     * Date --> Instant --> ZonedDateTime --> LocalDateTime
     */
    @Test
    public void convertDateToLocal() {
        Date date = new Date();
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
        // LocalDateTime localDateTime2 = LocalDateTime.ofInstant(instant, zoneId);
        System.out.println("Date = " + date);
        System.out.println("LocalDateTime = " + localDateTime);
    }

    /**
     * LocalDateTime --> ZonedDateTime --> Instant --> Date
     */
    @Test
    public void convertLocalToDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        System.out.println("LocalDateTime = " + localDateTime);
        System.out.println("Date = " + date);
    }

}
