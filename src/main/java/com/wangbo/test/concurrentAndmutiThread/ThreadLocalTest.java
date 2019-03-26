package com.wangbo.test.concurrentAndmutiThread;

import com.wangbo.test.clazz.TheClassTest.InnerClassPerson;


/**
 * ThreadLocal名为线程本地变量，或者线程本地存储。threadLocal为指定变量value在每个线程中创建一个副本，储存变量地址。
 * 本质为：
 *  以threadLocal作为entry的key，指定变量value作为entry的value，存入当前线程currentThread的实例变量ThreadLocalMap中.
 * 
 * 1.每次往当前线程的ThreadLocalMap中存入一个entry时，都会检查ThreadLocalMap的容器table的容量是否已经超载；
 * 2.如果容器table的容量已经超载size > threshold，则会将容器table扩容一倍（换成新的table）；
 * 3.通过方法get()获取当前线程中存储的副本，如果当前线程的ThreadLocalMap还未创建
 *   或者不存在与threadLocal对应的副本entry，则返回调用方法initialValue()创建的初始值；
 * 4.方法initialValue()是ThreadLocal的保护方法，可以被子类重写。
 * 
 * 特别地：java1.8可以通过withInitial指定子类SuppliedThreadLocal的initialValue()方法
 * 
 * 应用：ThreadLocal最常见的应用是解决数据库连接、Session管理
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2019年3月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ThreadLocalTest {
    private static final ThreadLocal<InnerClassPerson> threadLocal1 = new ThreadLocal<InnerClassPerson>() {
        @Override
        protected InnerClassPerson initialValue() {
            return new InnerClassPerson();
        }
    };

    private static final ThreadLocal<InnerClassPerson> threadLocal2 = ThreadLocal
            .withInitial(() -> new InnerClassPerson());

}


