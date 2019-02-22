package com.wangbo.test.java8.newinterface;

public interface HelloServiceA {
	void eatFood(String food);
	
//	void run(long time);
	
	default void sayHello() {
		System.out.println("hello,A号接口！");
	}
}
