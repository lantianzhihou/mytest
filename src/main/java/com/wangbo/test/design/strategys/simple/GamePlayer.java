package com.wangbo.test.design.strategys.simple;

import com.wangbo.test.design.pojo.CalPrice;
import com.wangbo.test.design.pojo.GoldVipPrice;
import com.wangbo.test.design.pojo.OriginPrice;
import com.wangbo.test.design.pojo.SuperVipPrice;
import com.wangbo.test.design.pojo.VipPrice;

public class GamePlayer {

    public static void main(String[] args) {
        GamePlayer player = new GamePlayer();
        player.buyGame(500D);
        System.out.println("玩家需要付钱：" + player.callActualPrice());
        player.buyGame(3000D);
        System.out.println("玩家需要付钱：" + player.callActualPrice());
        player.buyGame(4000D);
        System.out.println("玩家需要付钱：" + player.callActualPrice());
        player.buyGame(6000D);
        System.out.println("玩家需要付钱：" + player.callActualPrice());
    }

    private Double totalAmount = 0D;
    private Double amount = 0D;
    // 每个客户都有一个计算价格的策略，初始都是普通计算，即原价
    private CalPrice calPrice = new OriginPrice();

    public void buyGame(double price) {
        amount = price;
        totalAmount += price;
        if (totalAmount > 10000D) {
            calPrice = new SuperVipPrice();
            return;
        }
        if (totalAmount > 6000D) {
            calPrice = new GoldVipPrice();
            return;
        }
        if (totalAmount > 3000D) {
            calPrice = new VipPrice();
            return;
        }
    }

    // 计算客户实际要付的钱
    public Double callActualPrice() {
        return calPrice.calPrice(amount);
    }
}
