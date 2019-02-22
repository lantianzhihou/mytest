package com.wangbo.test.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanyuPinyinComparator implements Comparator<String> {

    @Override
    public int compare(String inputStr1, String inputStr2) {
        return compareHanyuPinyinFirstChar(inputStr1, inputStr2);
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

    public static int compareHanyuPinyinFirstChar(String inputStr1, String inputStr2) {
        return getHanyuPinyinFirstChar(inputStr1).compareTo(getHanyuPinyinFirstChar(inputStr2));
    }

    private static String getHanyuPinyinFirstChar(String inputStr) {
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();
        for (char cha : inputStr.toCharArray()) {
            if (Pattern.matches("[\u4e00-\u9fa5]", String.valueOf(cha))) {
                try {
                    sb.append(
                            PinyinHelper.toHanyuPinyinStringArray(cha, defaultFormat)[0].charAt(0));
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(cha);
            }
        }
        return sb.toString();
    }

}
