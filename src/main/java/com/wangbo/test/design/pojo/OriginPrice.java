package com.wangbo.test.design.pojo;

import com.wangbo.test.design.strategys.reflect.PriceRegion;

@PriceRegion(max = 3000)
public class OriginPrice implements CalPrice {

    @Override
    public Double calPrice(Double originalPrice) {
        return originalPrice;
    }

}
