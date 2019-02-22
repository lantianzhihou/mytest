package com.wangbo.test.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MetaAnnotationType {
	
	public static void main(String[] args) {
		
	}
}

// java 1.5
@Target(ElementType.TYPE) @interface TargetType{}                        //接口、类、枚举、注解  ,TYPE
@Target(ElementType.FIELD) @interface TargetField{}                      //字段、枚举的常量  ,FIEID（成员变量）
@Target(ElementType.METHOD) @interface TargetMethod{}                    //方法  ,METHOD（方法）
@Target(ElementType.PARAMETER) @interface TargetParamter{}               //方法参数  ,PARAMETER（参数）
@Target(ElementType.CONSTRUCTOR) @interface TargetConstructor{}          //构造函数  ,CONSTRUCTOR（构造方法）
@Target(ElementType.LOCAL_VARIABLE) @interface TargetLocalVariable{}     //局部变量  ,LOCAL_VARIABLE（变量）
@Target(ElementType.ANNOTATION_TYPE) @interface TargetAnnotationType{}   //注解  ,ANNOTATION_TYPE（注解）,元注解

@Target(ElementType.PACKAGE) 
@Retention(RetentionPolicy.RUNTIME)
@interface TargetPackage{String version() default "1.0";}   //PACKAGE（包）

// java 1.8
@Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})  @interface TypeAnnotation {} 
//ElementType.TYPE_PARAMETER 表示该注解能写在类型变量的声明语句中
//ElementType.TYPE_USE 表示该注解能写在使用类型的任何语句中（eg：声明语句、泛型和强制转换语句中的类型）

@TargetAnnotationType@TypeAnnotation @interface TargetAll{}                                                    
  
@Retention(RetentionPolicy.SOURCE) @interface RetentionSource{}  
@Retention(RetentionPolicy.CLASS) @interface RetentionClass{}  
@Retention(RetentionPolicy.RUNTIME) @interface RetentionRuntime{}  
  
@Documented @interface WbDocument{}  
  
@Inherited
@interface WbInherited {
}
