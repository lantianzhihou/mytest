package com.wangbo.test.java8.newinterface;

import org.junit.Test;

public class TestNewInterface extends Adapter implements HelloServiceB {
    @Test
    public void displayNewInterface() {
        sayHello();
    }
    
    @Override
    public void eatFood(String food) {
        System.out.println("java8新继承规则在吃饭！");
    }
    
    /**
     * 2.接口允许定义静态static方法和默认default方法，以及抽象abstract方法三种；
     *          实现接口的类或者子接口不会继承接口中的静态方法。static不能和default同时使用，
     *          其中静态方法必须有方法体body，而且 只能通过接口类名调用不允许实现类对象或者匿名类对象调用，
     *          其中默认方法必须有方法体body，而且只能通过接口类的实现类对象或者匿名类对象调用。
     *          
     *          默认方法提供了2个优点：
     *          (@ https://www.cnblogs.com/LinkinPark/p/5232972.html)
     *          优雅的随时间演化接口，也就是代码的向后兼容性
     *          提供可选功能，类不必在不需要该功能的时候提供占位符实现。
     * 
     *          接口中默认方法在一定程度上提供了行为的多继承(接口不能维护状态信息即字段)===
     *              但是多继承会引起一个问题------冲突(定义一组规则)：
     *              1）在所有的情况，类实现的优先级高于接口的默认实现，也就是先使用自己类中定义的方法或者是父类中的方法。
     *              类优先是解决上面系列问题的最主要的规则。
     *              2）当类同时实现了2个接口，2个接口中如果包含相同的(或默认)方法，那么这个类中就必须重写这个接口，不然代码报错。
     *              3）如果是一个接口继承了另外一个接口，2个接口中也包含相同的默认方法，那么继承接口的版本具有更高的优先级。
     *              比如A扩展了B接口，那么优先使用A类里面的test方法。
     *              4）在接口或者实现类中通过使用super，可以显式的引用被继承接口的默认实现，语法如下：
     *              InterfaceName.super.methodName()。
     */
    @Override
    public void sayHello() {
        HelloServiceB.super.sayHello();
        super.sayHello();
        System.out.println("hello,实现类！");
        /**
         * 在接口或者实现类中不能通过使用super显式的引用被继承接口的父类接口的默认实现,
         */
        // HelloServiceA.super.sayHello();
    }
}
