package com.wangbo.test.design.strategys.factory;

import com.wangbo.test.design.pojo.CalPrice;
import com.wangbo.test.design.pojo.OriginPrice;

public class ToyBuyer {

    public static void main(String[] args) {
        ToyBuyer buyer = new ToyBuyer();
        buyer.buy(500D);
        System.out.println("玩家需要付钱：" + buyer.calActualPrice());
        buyer.buy(3000D);
        System.out.println("玩家需要付钱：" + buyer.calActualPrice());
        buyer.buy(4000D);
        System.out.println("玩家需要付钱：" + buyer.calActualPrice());
        buyer.buy(6000D);
        System.out.println("玩家需要付钱：" + buyer.calActualPrice());
    }

    private Double amount = 0D;
    private Double totalAmount = 0D;
    private CalPrice calPrice = new OriginPrice();

    public void buy(Double amount) {
        this.amount = amount;
        this.totalAmount += amount;
        /* 我们将策略的制定转移给了策略工厂，将这部分责任分离出去 */
        this.calPrice = CalPriceFactory.getCalPrice(this);
    }

    public Double calActualPrice() {
        return calPrice.calPrice(this.amount);
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

}
