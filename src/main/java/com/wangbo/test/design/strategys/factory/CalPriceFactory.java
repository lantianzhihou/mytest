package com.wangbo.test.design.strategys.factory;

import com.wangbo.test.design.pojo.CalPrice;
import com.wangbo.test.design.pojo.GoldVipPrice;
import com.wangbo.test.design.pojo.OriginPrice;
import com.wangbo.test.design.pojo.SuperVipPrice;
import com.wangbo.test.design.pojo.VipPrice;

public class CalPriceFactory {
    private CalPriceFactory() {}
    
    public static CalPrice getCalPrice(ToyBuyer buyer) {
        if (buyer.getTotalAmount() > 10000D) {
            return new SuperVipPrice();
        }
        if (buyer.getTotalAmount() > 6000D) {
            return new GoldVipPrice();
        }
        if (buyer.getTotalAmount() > 3000D) {
            return new VipPrice();
        }
        return new OriginPrice();
    }

    public static <T> T getCalPriceImpl(Class<T> clazz) throws Exception {
        if (CalPrice.class.isAssignableFrom(clazz)) {
            return clazz.newInstance();
        }
        throw new RuntimeException("");
    }
}
