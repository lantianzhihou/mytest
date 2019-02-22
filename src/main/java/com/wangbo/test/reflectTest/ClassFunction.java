package com.wangbo.test.reflectTest;

import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * <1.Java对于每一个类都是看作顶层类的，哪怕所谓的嵌套类（内部类）编译后都会生成一个单独类文件，这也是为什么会生成Main$inner的原理，
 * 也就是说，编译后，内外部两个类表面上其实就没啥关系了（但是内部非静态类只有外部类可以访问，这种权限认定不知道怎么实现的）;
 * 2.拥有内部类的类编译后内外部类两者没有关系，那么私有内部类编译后默认是没有对外构造器的
 * （如果以上代码中在Inner手动给一个public的构造器，Main$1是不会出现的），但是外部类是可以引用内部类的;
 * 3.编译器通过生成一些在源代码中不存在的synthetic方法和类的方式，实现了对private级别的字段和类的访问，从而绕开了语言限制
 * >
 * 
 * https://www.cnblogs.com/bethunebtj/p/7761596.html
 * 
 * @author 姓名 工号
 * @version [版本号, 2018年1月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ClassFunction {

	public static void main(final String[] arguments) throws SecurityException, ClassNotFoundException, NoSuchMethodException {
		ClassFunction.NestedClass nested = new ClassFunction.NestedClass();
		
//		Class<?> clazz = Class.forName("com.wangbo.study.reflectTest.ClassFunction$NestedClass");
//		Method meth = clazz.getMethod("access$1",clazz);
//		System.out.println(meth);
		
		for (Method method : Class.forName("com.wangbo.study.reflectTest.ClassFunction$NestedClass").getMethods()) {
			System.out.println(method.getName() + " : " + method.isSynthetic());
		}
		
		System.out.println("String: " + nested.highlyConfidential);
		System.out.println("String: " + nested.highlyConfidentialInt);
		System.out.println("String: " + nested.highlyConfidentialCalendar);
		System.out.println("String: " + nested.highlyConfidentialBoolean);
	}

	private static final class NestedClass {
		private NestedClass() {};
		private String highlyConfidential = "Don't tell anyone about me";
		private int highlyConfidentialInt = 42;
		private Calendar highlyConfidentialCalendar = Calendar.getInstance();
		private boolean highlyConfidentialBoolean = true;
	}

	public static void commonDisplay() {
		// 是否为基本类型，4类8中加上void
		System.out.println("int is primitive ? " + int.class.isPrimitive());
		// 是否为合成类
		System.out.println("int is synthetic ? " + int.class.isSynthetic());

		// 是否为基本类型，4类8中加上void
		System.out.println("String is primitive ? " + String.class.isPrimitive());
		// 是否为合成类
		System.out.println("String is synthetic ? " + String.class.isSynthetic());

		// 是否为基本类型，4类8中加上void
		System.out.println("ClassFunction is primitive ? " + ClassFunction.class.isPrimitive());
		// 是否为合成类
		System.out.println("ClassFunction is synthetic ? " + ClassFunction.class.isSynthetic());
	}
}
