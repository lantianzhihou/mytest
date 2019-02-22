package com.wangbo.test.codeLump;

import java.util.HashMap;
import java.util.Map;

/**
 *	使用{}括起来的代码称为代码块，根据位置和声明的不同分为静态代码块、构造代码块、局部代码块
 *	1.静态代码块使用static修饰，仅在类class对象被使用时调用一次，作用是对类进行初始化；
 *	2.构造代码块在类成员位置，每次调用构造方法前都会执行构造代码块，可以将构造方法中的共同代码放到一起，对对象进行初始化；
 *	3.局部代码块在方法体中，用于限定变量的生命周期，在局部代码块中定义的变量外部无法访问。	
 * 
 * 	执行顺序：
 * 		静态代码块 -- >构造代码块 --> 构造方法
 * 		静态代码块：只执行一次
 * 		构造代码块：每次调用构造方法都执行
 * 
 * @author  姓名 工号
 * @version  [版本号, 2018年2月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CodeLump extends FatherCodeLump {
	
    /**
     * 1.类的构造器<clinit>方法是由编译器收集类中的所有类变量的赋值操作，加上静态代码块组成，按照源代码的编写顺序确定执行顺序；
     * 2.虚拟机会保证在子类的<clinit>方法执行之前，父类的<clinit>方法已经执行完毕；
     * 3.如果一个类中没有静态变量的赋值操作，也没有静态代码块，编译器不会生成<clinit>方法；
     * 4.接口中不能使用静态代码块，但可以使用静态变量，所有编译器也可能会生成<clinit>方法，
     * 执行接口的初始化<clinit>方法不需要先执行父接口的<clinit>方法，除非本接口引用了父接口的静态变量；
     * 5.虚拟机可以保证一个类的<clinit>方法是线程安全的
     */
    static {
        /**
         * 静态代码块只能访问定义在代码块之前的类变量；对于定义在代码块之后的类变量只能进行赋值操作，但不能访问
         */
        name = "13";
//        System.out.println("静态代码块！age = " + age);
    }

    private static String name = "12";
	
    @SuppressWarnings("all")
    public static void main(String[] args) {
        CodeLump codeLump = new CodeLump() {
            {
                System.out.println("隐匿内部类构造代码块！age = " + this.getAge());
                addAge(5);
            }
        };
        {
            System.out.println("局部代码块！name = " + codeLump.name);
            if (codeLump != null) {
                System.out.println(codeLump);
            }
        }

        Map<String, String> map = new HashMap<String, String>() {
            // 匿名内部类中的构造代码块，对匿名对象就行初始化
            {
                put("1", "ni1");
                put("2", "ni2");
                put("3", "ni3");
            }
        };
        System.out.println(map);
    }

    public int getAge() {
        return age;
    }

    public void addAge(int age) {
        this.age = age;
    }

    public CodeLump() {
        super();

        // action = "eat";
        System.out.println("构造方法！age = " + age);
        this.age = 2;
        age = 4;
    }

	{
        // addName("构造代码块");
        age = 3;
        // System.out.println("构造代码块！name = " + name);
	}
	
    private int age = 1;
    private final String action = "eat";


}
