package com.wangbo.test.java8.newinterface;

/**
 * 	java1.8编译器默认会将所有满足条件的接口都当成函数式接口：
 * 		接口中有且只有一个抽象方法
 * 	默认方法和静态方法，以及和Object中的方法相同的抽象方法均不计入数量中
 * 	does <em>not</em> count toward the interface's abstract method count
 */
@FunctionalInterface
public interface HelloServiceB extends HelloServiceA{
	void eatFood(String food);
//	void run(long time);
	
	default void sayHello() {
		HelloServiceA.super.sayHello();
		System.out.println("hello,B号接口！");
	}
	
	String toString();
}
