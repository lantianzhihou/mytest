/**
 * <b>package-info不是平常类，其作用有三个:</b><br>
 * 1、为标注在包上Annotation提供便利；<br>
 * 2、声明包的私有类和常量；<br>
 * 3、提供包的整体注释说明。<br>
 * 4、package-info.java里不能声明public class(或public interface)
 * 5、提供包级别的类(或接口)，这些类(或接口)都是使用默认权限修饰符，因此只有本包里才能访问，即使是子包也不能访问
 * 6、Package annotations must be in file package-info.java
 * 
 * @author wangbo86lantian@163.com
 */
@com.wangbo.test.annotation.TargetPackage(version = "1.0")
package com.wangbo.test.annotation;

//类编译后没有任何修饰关键字
class PackageInfo {
	public static final String NAME = "annotation";
	
	public void common(){  
        System.out.println("sa");  
    }
}

class PackageInfoGeneric<T extends Throwable>{  
    private T obj;  
    public void set(T obj){  
        this.obj = obj;  
    }  
    public void common(){  
        System.out.println(obj + "===sa");  
    }  
}

// 接口编译后是有abstract修饰
@TypeAnnotation
interface PackageInfoInteger<@TypeAnnotation T>{ 
	public String ERROE_CODE = "100001";
	
    public void test();  
}  


