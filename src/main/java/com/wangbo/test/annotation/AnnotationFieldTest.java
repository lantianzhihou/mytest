package com.wangbo.test.annotation;

import org.apache.ibatis.annotations.Param;

import com.wangbo.test.annotation.AnnotationFieldType.EnumLamp;

//注解：为注解添加属性值  
@AnnotationFieldType(annotationClass = RepeatableFunction.class, annotationAttr = @Param("wangbo"), color = "black", value = "abc", arrayArr = {
		1, 3, 123 }, lamp = EnumLamp.GREEN)
public class AnnotationFieldTest {

	// 若在设置值时只有一个value属性需要设置 ，那么value可以省略，直接写所设置的值即可。
	@AnnotationFieldType("xyz")
	@SuppressWarnings("deprecation") // 注解：告诉编译器或者开发工具……
	// 若在设置值时只有一个value属性需要设置 ，那么value可以省略，直接写所设置的值即可。
	// 上面"deprecation"即是省略的value后的值。

	public static void main(String[] args) throws Exception {
		System.runFinalizersOnExit(true);// 中间带横线的部分，说明该方法已经过时了。

		// 检查某注解是否存在，使用反射;并返回该注解
		if (AnnotationFieldTest.class.isAnnotationPresent(AnnotationFieldType.class)) {
			// 获取注解
			AnnotationFieldType annotation = (AnnotationFieldType) AnnotationFieldTest.class
					.getAnnotation(AnnotationFieldType.class);
			// 获取属性值
			// 调用color属性方法
			System.out.println("属性color值：" + annotation.color());
			System.out.println(annotation.value());
			// 这里输出的是数组长度，不能直接输出数组的每一个值，调用得到的是数组哈希地址值
			System.out.println(annotation.arrayArr().length);
			System.out.println(annotation.lamp().name());
			System.out.println(annotation.annotationAttr().value());
			// 获取类类型注解中的 类名称
			System.out.println(annotation.annotationClass());

		}
	}

	@Deprecated // 表示下面的方法过时了
	public static void sayHello() {
		System.out.println("hi");
	}

}
