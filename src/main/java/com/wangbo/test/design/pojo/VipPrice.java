package com.wangbo.test.design.pojo;

import com.wangbo.test.design.strategys.reflect.PriceRegion;

@PriceRegion(min = 3000, max = 6000)
public class VipPrice implements CalPrice {

    @Override
    public Double calPrice(Double originalPrice) {
        return originalPrice * 0.9;
    }

}
