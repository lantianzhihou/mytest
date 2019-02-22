package com.wangbo.test.annotation;

import java.io.IOException;

@TypeAnnotation
public@TargetType class AnnotationTypeTest {
	
	//	@TypeAnnotation 成员变量上方或者类型前面效果一样
	@TargetField
	private @TypeAnnotation Integer age;
	
	/**
	 * 	@TypeAnnotation 其实本质不是用来标注方法的,而是用来标注方法返回值类型的，
	 * 	但是method类型的注解可以修饰返回值类型为void的方法，而type_use 不行
	 */
	public @TargetMethod Integer getAge() {
		return age;
	}
	
//	@TypeAnnotation Type annotation is illegal for a method that returns void
	@TargetMethod
	public void setAge(Integer age) {
		this.age = age;
	}
	
	@TypeAnnotation
	public @TargetConstructor AnnotationTypeTest() {
		super();
	}
	
	/*@TypeAnnotation 标注void错误*/
	public static void main(String[] args) {
		
	}
	
	/**
	 *  @ElementType.TYPE_USE支持注解出现的位置：注解能写在使用类型的任何语句中（eg：声明语句、泛型和强制转换语句中的类型）
	 *  1.创建类实例
	 *  2.类型映射、强转
	 *  3.implements 语句中
	 *  4.泛型中
	 *  5.throw exception声明
	 *  
	 *  6.标注类 ,接口,枚举
	 *  7.标注构造方法
	 *  8.标注返回值类型不为void的方法
	 *  9.标注成员变量
	 *  10.标注局部变量
	 *  11.标注方法参数
	 *  12.作为元注解标注注解
	 *  
	 *  13.不能用来标注包
	 */
	@TypeAnnotation 
	public @TargetMethod String display(@TypeAnnotation@TargetParamter String name) throws@TypeAnnotation IOException {
		@TypeAnnotation@TargetLocalVariable String alias = "wangbo";
		Object obj = new @TypeAnnotation String("abc");
		alias = (@TypeAnnotation String)obj;
		return alias;
	}
}
