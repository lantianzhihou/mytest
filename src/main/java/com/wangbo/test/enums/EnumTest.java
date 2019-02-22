package com.wangbo.test.enums;

public class EnumTest {
	
	public static void main(String[] args) {
		displayEnum ();
		
		testEnumEqual();
		
		System.out.println(SimpleEnum.SPIRNG.compareTo(SimpleEnum.SUMMER));
	}
	
	public static void displayEnum () {
		for (SimpleEnum simpleEnum : SimpleEnum.values()	) {
			System.out.println(simpleEnum);
		}
	}
	
	/** 
	 * 测试枚举比较,使用equal和== ,都一样
	 */  
	private static void testEnumEqual() {  
		SimpleEnum s1 = SimpleEnum.SPIRNG;  
		SimpleEnum s2 = SimpleEnum.SPIRNG;  
		SimpleEnum ss1 = SimpleEnum.SUMMER;  
	    System.out.println("s1 == s2：" + (s1 == s2));  
	    System.out.println("s1.equals(s2)：" + (s1.equals(s2)));  
	  
	    System.out.println("s1 == ss1：" + (s1 == ss1));  
	    System.out.println("s1.equals(ss1)：" + (s1.equals(ss1)));  
	} 
}
