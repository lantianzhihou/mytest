package com.wangbo.test.design.singleton;

/**
 * 利用枚举实现单例模式的优势：
 *  1.天生避免多线程安全问题
 *  2.自动支持序列化机制，绝对防止实例化，keyi通过反序列化返回原枚举对象
 *  3.天生拒绝clone()方法创建对象
 *  4.不能通过reflection attack来调用私有化构造方法
 * @author 姓名 工号
 * @version [版本号, 2018年11月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public enum EnumSingle {
    INSTANCE;
}
