package com.wangbo.test.design.strategys.reflect;

import com.wangbo.test.design.pojo.CalPrice;

public class FoodBuyer {

    public static void main(String[] args) {
        FoodBuyer buyer = new FoodBuyer();
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
    private CalPrice calPrice;

    public void buy(Double amount) {
        this.amount = amount;
        this.totalAmount += amount;

        calPrice = FoodPriceFactory.getInstance().getCalPrice(this);
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public Double calActualPrice() {
        return calPrice.calPrice(this.amount);
    }
}
