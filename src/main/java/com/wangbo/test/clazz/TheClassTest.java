package com.wangbo.test.clazz;

/**
 * 在java源文件中可以定义一个非内部类，也可以定义多个非内部类，被public修饰的非内部类最多只能有一个。
 *  1.没有public修饰的非内部类只允许本包内访问，如clazz包下的InheritedAnnotation可以访问InnerJavaPerson;
 *  2.annotation包下的类不能访问InnerJavaPerson。
 *  
 * 在一个类中可以定义多个内部类,显式内部类作为该类的成员变量而存在，匿名内部类作为局部变量存在
 *  (权限修饰符private、protected、默认、public)
 *  1.默认权限修饰符的内部类只允许本包内访问构造函数,如InnerInstancePerson;
 *  2.protected修饰的内部类只允许外部类及外部类的子类访问该内部类构造函数,如InnerClassPerson;
 *  3.private修饰的内部类只允许外部类访问内部类的构造函数。
 *  
 *  (静态关键字static)
 *  1.static修饰的内部类的构造函数，其他类可以直接访问,如new TheClassTest.InnerClassPerson(),TheClassTest可以省略不写;
 *  2.没有static修饰的内部类的构造函数，其他类只能通过外部类的实例对象来访问,如new TheClassTest().new InnerInstancePerson().
 *  
 * 在类变量的初始化过程中即<clinit>方法中只能调用静态方法、静态变量：
 *  1.在innerJavaSupply的初始化中innerJavaPerson必须为静态变量；
 *  2.在innerInstanceSupply1的初始化中InnerInstancePerson为非静态类，必须通过外部类的实例对象调用其构造函数；
 *  3.在InnerClassSupply的初始化中InnerClassPerson为非内部类，可以直接访问其构造函数。
 *   
 * 注意：innerInstanceSupply2为TheClassTest的实例变量，其初始化是在外部类的构造函数中，
 *  而内部类InnerInstancePerson作为实例变量，在外部类构造函数中可以直接访问其构造函数；
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  姓名 工号
 * @version  [版本号, 2019年3月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TheClassTest {
    private static InnerJavaPerson innerJavaPerson = new InnerJavaPerson();

    private static final ThreadLocal<InnerJavaPerson> innerJavaSupply = ThreadLocal.withInitial(() -> innerJavaPerson);

    /**
     * No enclosing instance of type TheClassTest is accessible. Must qualify
     * the allocation with an enclosing instance of type TheClassTest (e.g.
     * x.new A() where x is an instance of TheClassTest).
     * 
     * Illegal enclosing instance specification for type
     * TheClassTest.InnerInstancePerson.
     * 
     * 特别地：在innerInstanceSupply1的初始化中InnerInstancePerson为非静态类，
     * 必须通过外部类的实例对象调用其构造函数；
     */
    private static final ThreadLocal<InnerInstancePerson> innerInstanceSupply1 = ThreadLocal
            .withInitial(() -> new TheClassTest().new InnerInstancePerson());
    private final ThreadLocal<InnerInstancePerson> innerInstanceSupply2 = ThreadLocal
            .withInitial(() -> new InnerInstancePerson());

    private static final ThreadLocal<InnerClassPerson> InnerClassSupply = ThreadLocal
            .withInitial(() -> new InnerClassPerson());

    class InnerInstancePerson {
        public InnerInstancePerson() {

        }
    }

    public static class InnerClassPerson {
        public InnerClassPerson() {

        }
    }
}

class InnerJavaPerson {
    public InnerJavaPerson() {

    }
}
