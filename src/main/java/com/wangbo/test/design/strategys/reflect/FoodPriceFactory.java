package com.wangbo.test.design.strategys.reflect;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.wangbo.test.design.pojo.CalPrice;

public class FoodPriceFactory {

    private static final String STRATEGY_PACKAGE_NAME = "com.wangbo.study.design.pattern.strategys";
    private static final String CLAZZ_SUFFIX_NAME = ".class";

    // 策略列表
    public List<CalPrice> calPrices;
    private ClassLoader classLoader = getClass().getClassLoader();;

    private FoodPriceFactory() {
        init();
    }

    @SuppressWarnings("unchecked")
    private void init() {
        File[] classFiles = getResources(STRATEGY_PACKAGE_NAME);
        Class<CalPrice> priceInterface = null;
        try {
            priceInterface = (Class<CalPrice>) classLoader.loadClass(CalPrice.class.getName());
        } catch (Exception e) {
            throw new RuntimeException("未找到价格策略接口");
        }
        calPrices = new ArrayList<>();
        try {
            for (File classFile : classFiles) {
                Class<?> clazz = classLoader
                        .loadClass(STRATEGY_PACKAGE_NAME + "." + classFile.getName().replace(CLAZZ_SUFFIX_NAME, ""));
                if (CalPrice.class.isAssignableFrom(clazz) && clazz != priceInterface) {
                    Annotation[] annotations = clazz.getDeclaredAnnotations();
                    for (Annotation annotation : annotations) {
                        if (annotation instanceof PriceRegion) {
                            calPrices.add(((Class<? extends CalPrice>) clazz).newInstance());
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("获取价格策略失败");
        }
    }

    private File[] getResources(String packageName) {
        try {
            File file = new File(classLoader.getResource(packageName.replace(".", "/")).toURI());
            return file.listFiles(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    // 只扫描class文件
                    if (file.getName().endsWith(CLAZZ_SUFFIX_NAME)) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("获取策略资源失败");
        }
    }

    public static FoodPriceFactory getInstance() {
        return FoodPriceFactoryBuilder.FACTORY_INSTANCE;
    }

    public CalPrice getCalPrice(FoodBuyer buyer) {
        Double totalAmount = buyer.getTotalAmount();
        for (CalPrice calPrice : calPrices) {
            PriceRegion priceRegion = calPrice.getClass().getAnnotation(PriceRegion.class);
            if (totalAmount > priceRegion.min() && totalAmount <= priceRegion.max()) {
                return calPrice;
            }
        }
        throw new RuntimeException("找不到对应的价格策略");
    }

    private static class FoodPriceFactoryBuilder {
        private static final FoodPriceFactory FACTORY_INSTANCE = new FoodPriceFactory();
    } 
}
