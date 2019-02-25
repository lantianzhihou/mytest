package com.wangbo.test.reflectTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import sun.reflect.FieldAccessor;
import sun.reflect.ReflectionFactory;

public class ReflectionHelper {
	private static final String MODIFIERS_FIELD = "modifiers";

	@SuppressWarnings("restriction")
	private static final ReflectionFactory reflection = ReflectionFactory.getReflectionFactory();

	private static final String ParameterizedType = null;

	/*
	 * type 是所有类型的高级公共接口，当然也是Class 的父类 它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
	 * 
	 * 先来看一下 Type 的用法 type 是一种表示编程语言中的所有类型的类超级接口，如 int Integer String
	 * 这都是表示一编程语言的类型，而其中的 int.class Integer.class String.class 它们表示的是类型的实例
	 * 如果，我们前面学习的反射 Class c = Integer.class,Class 相当于表示类型的类,而Integer.class 则是一种
	 * 名为整形类型的类型实例 理解了上面的那些，其 理解 type 就不难了，type 与 class 一样，不过 type 是一种比 Class
	 * 表示范围更加广的 超级接口，它表示Java语言中所有类型的接口
	 */

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method method = ReflectionHelper.class.getMethod("testParamterType", ReflectionHelper.ReflectionInner.class);
		// 得到参数类型
		Type[] paramTypes = method.getParameterTypes();
		// 得到参数化型参数
		Type[] genericParamTypes = method.getGenericParameterTypes();
		System.out.println(paramTypes[0]);			// interface java.util.List
		System.out.println(genericParamTypes[0]); 	// java.util.List<? extends com.wangbo.study.reflectTest.Human>
		
		Type type = genericParamTypes[0];
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			// 得到参数化型参数ReflectionHelper.ReflectionInner<? extends Human>的参数(? extends Human)
			Type[] actualTypeArguments = paramType.getActualTypeArguments();
			Type ownerType = paramType.getOwnerType(); // class com.wangbo.study.reflectTest.ReflectionHelper
			Type rawType = paramType.getRawType();
			// com.wangbo.study.reflectTest.ReflectionHelper.com.wangbo.study.reflectTest.ReflectionHelper$ReflectionInner<? extends com.wangbo.study.reflectTest.Human>
		}
	}

	public void testParamterType(ReflectionHelper.ReflectionInner<? extends Human> humans) {

	}
	
	class ReflectionInner<T> {
		
	}

	public static void setStaticFinalField(Field field, Object value)
			throws NoSuchFieldException, IllegalAccessException {
		// 获得 public 权限
		field.setAccessible(true);
		// 将modifiers域设为非final,这样就可以修改了
		Field modifiersField = Field.class.getDeclaredField(MODIFIERS_FIELD);
		modifiersField.setAccessible(true);
		int modifiers = modifiersField.getInt(field);
		// 去掉 final 标志位
		modifiers &= ~Modifier.FINAL;
		modifiersField.setInt(field, modifiers);
		FieldAccessor fa = reflection.newFieldAccessor(field, false);
		fa.set(null, value);
	}
}