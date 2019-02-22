package com.wangbo.test.design.pojo;

import com.wangbo.test.design.strategys.reflect.PriceRegion;

@PriceRegion(min = 6000, max = 10000)
public class GoldVipPrice implements CalPrice {

    @Override
    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.8;
    }

}
