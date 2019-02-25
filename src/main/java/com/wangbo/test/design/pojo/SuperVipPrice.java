package com.wangbo.test.design.pojo;

import com.wangbo.test.design.strategys.reflect.PriceRegion;

@PriceRegion(min = 10000)
public class SuperVipPrice implements CalPrice {

    @Override
    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.7;
    }

}
