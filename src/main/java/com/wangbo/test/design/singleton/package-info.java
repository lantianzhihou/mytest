/**
 * 单例模式：一个全局使用的类为了避免对象的频繁创建和销毁，造成资源浪费，只允许创建一个对象。
 *  1.构造方法私有化
 *  2.提供一个静态方法getInstance()获取唯一实例对象
 * 
 * 分为饿汉式和懒汉式
 *  1.因为饿汉式单例是在类加载时就创建了单例对象，浪费内存，但不会有多线程安全问题
 *  2.懒汉式单例因为要考虑到多线程安全的问题，常用的有效方式有三种：
 *      双检锁式（double-checked lock）加上关键字volatile；第二个if判断语句通过synchronized进行同步
 *      登记式/静态内部类（singleton-holder）
 *      枚举式
 * 
 * @author  姓名 工号
 * @version  [版本号, 2018年11月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
package com.wangbo.test.design.singleton;