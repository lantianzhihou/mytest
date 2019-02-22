package com.wangbo.test.annotation;

import java.io.IOException;

/** 
 * 测试package-info.java文件的作用 
 * 1、为标注在包上Annotation提供便利；<br>   
 * 2、声明包的私有类和常量；<br>   
 * 
 */ 
public class PkgInfoTest {

	public static void main(String[] args) {
		Package pkg = PkgInfoTest.class.getPackage();
		System.out.println(pkg.getImplementationVersion());
		
		TargetPackage annotation = pkg.getAnnotation(TargetPackage.class);
		if(annotation != null) {
			System.out.println("package version:" + annotation.version());
		}
		
		PackageInfo packageInfo = new PackageInfo();
		packageInfo.common();
		
		//泛型也能很好的工作，在pakcage-info.java里定义的类和普通类没什么区别
		PackageInfoGeneric<Exception> packageInfoGeneric = new PackageInfoGeneric<>();
		packageInfoGeneric.set(new IOException("device io"));  
        packageInfoGeneric.common();
        
        Sub sub = new Sub(); 
        sub.test();
        System.out.println(PackageInfoInteger.ERROE_CODE);
	}
}

class Sub implements PackageInfoInteger<@TypeAnnotation String> {
	@Override
	public void test() {
		System.out.println("sub");
	}

}
