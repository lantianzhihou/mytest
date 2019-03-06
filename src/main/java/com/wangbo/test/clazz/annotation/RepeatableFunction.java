package com.wangbo.test.clazz.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

import org.apache.logging.log4j.core.config.Order;
import org.springframework.boot.logging.log4j2.SpringBootConfigurationFactory;
import org.springframework.stereotype.Service;

/**
 * java8 新增的@Repeatable注解，其实只是语法糖而已． 
 * java8 注解的 {@link RepeatAnn} 类与{@link Annotations}是等价的．
 *  新注解讲语法糖转化为注解值为数组形式．
 * 
 * @author 姓名 工号
 * @version [版本号, 2018年1月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class RepeatableFunction {
	
	/**
	 * 1.The annotation @Repeatable must define the attribute value
	 * 2.Type mismatch: cannot convert from Class<String> to Class<? extends Annotation>
	 * 3.The container annotation type @Chapter2Code.Member must declare a member value()
	 * 4.The value method in the container annotation type @Chapter2Code.Member must be of type Chapter2Code.Role[] but is String[]
	 */
	public static void main(String[] args) {
//		Annotation[] annotations3 = SpringBootConfigurationFactory.class.getDeclaredAnnotations();
//		Order order = SpringBootConfigurationFactory.class.getAnnotation(Order.class);
//		org.springframework.core.annotation.Order order2 = SpringBootConfigurationFactory.class.getAnnotation(org.springframework.core.annotation.Order.class);
		
		Annotation[] annotations = RepeatAnn.class.getAnnotations();
		System.out.println(annotations.length); // 1
		Arrays.stream(annotations).forEach(System.out::println);
		/**
		 * @com.wangbo.study.annotation.Chapter2Code$Member(value=[
		 * 		@com.wangbo.study.annotation.Chapter2Code$Role(name=doctor), 
		 * 		@com.wangbo.study.annotation.Chapter2Code$Role(name=who)])
		 */

		Annotation[] annotations2 = Annotations.class.getAnnotations();
		System.out.println(annotations2.length);// 1
		Arrays.stream(annotations2).forEach(System.out::println);
		/**
		 * @com.wangbo.study.annotation.Chapter2Code$Roles(member=[
		 * 		@com.wangbo.study.annotation.Chapter2Code$Role(name=doctor), 
		 * 		@com.wangbo.study.annotation.Chapter2Code$Role(name=who)])
		 */
		Role annotation3 = RepeatAnn.class.getAnnotation(Role.class);
		Member annotation4 = RepeatAnn.class.getAnnotation(Member.class);
		System.out.println(annotation3); // null
		System.out.println(annotation4); // @com.wangbo.study.annotation.RepeatableFunction$Member
		
		System.out.println(Arrays.asList(annotation4.annotationType().getAnnotations()));
		
		Role[] annotations5 = RepeatAnn.class.getAnnotationsByType(Role.class);
		System.out.println(annotations5.length);// 2
		Arrays.stream(annotations5).forEach(System.out::println);
		/**
		 * 	@com.wangbo.study.annotation.Chapter2Code$Role(name=doctor), 
		 * 	@com.wangbo.study.annotation.Chapter2Code$Role(name=who)])
		 */
	}

	/**
	 * The same annotation can be applied to a declaration or type more than once,
	 * given that each annotation is marked as @Repeatable. In the following code,
	 * the @Repeatable annotation is used to develop an annotation that can be
	 * repeated, rather than grouped together as in previous releases of Java. In
	 * this situation, an annotation named Role is being created, and it will be
	 * used to signify a role for an annotated class or method.
	 * 
	 * @author doctor
	 * 
	 * @since 2015年2月3日 下午8:51:09
	 */
	@Repeatable(value = Member.class)
	@Target({ElementType.TYPE_USE,ElementType.METHOD})
	public static @interface Role {
		String name() default "doctor";
	}
	
	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface Roles {
		Role[] member();
	}
	
	@Target({ElementType.TYPE,ElementType.METHOD})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	@Service
	public static @interface Member {
		Role[] value() default{@Role(name = "doctor"), @Role(name = "who")};
	}

	@Role(name = "doctor")
	@Role(name = "who")
	public static class RepeatAnn {

	}

	@Roles(member = { @Role(name = "doctor"), @Role(name = "who") })
	public static class Annotations {

	}
}