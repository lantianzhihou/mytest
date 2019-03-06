package com.wangbo.test.clazz.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.annotations.Param;

/**
 * 注解的属性类型:
 * 		原始类型（就是八个基本数据类型）、String类型、Class类型、数组类型、枚举类型、注解类型
 * 
 * @author  姓名 工号
 * @version  [版本号, 2018年1月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
//元注解：注解的注解。此注解表示使注解保留到运行时。
@Retention(RetentionPolicy.RUNTIME) 
// Target中可以存放数组。ElementType.METHOD：表示只能标记在方法上。ElementType.TYPE：表示只能标记定义在类上、接口上、枚举上等。
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface AnnotationFieldType {

	// 是一个特殊的属性，若在设置值时只有一个value属性需要设置 ，那么value可以省略，直接写所设置的值即可。
	String value();
	
	String color() default "blue"; // 表示有一个color属性，以方法的形式。设置默认属性值为蓝色
	
	// 数组属性
	int[] arrayArr() default { 3, 4, 5, 5 };

	// 枚举类型属性,这里使用了自定的枚举类EnumDemo.java
	EnumLamp lamp() default EnumLamp.RED;

	// 注解类型属性，此时关联到注解类：MetaAnnotation.java,并指定缺省值
	Param annotationAttr() default @Param("annotation");

	// Class类属性：设置默认值 ReflectDemo.java 类
	@SuppressWarnings("rawtypes")
	Class annotationClass() default RepeatableFunction.class;
	
	enum EnumLamp {
		RED,BLUE,GREEN;
	}

}