package com.wangbo.test.util.math;

import org.junit.Test;

public class MathOperationSymbol {

	/**
	 * ++a和a++的区别： 
	 * 1.a++ 是先取 a 的值再自增
	 * 2.++a 刚好相反，是先自增再取 a 的值
	 */
	@Test
	public void test1() {
		int[] intArr = { 0, 1, 2, 3, 4, 5, 6 };
		int i = 0;
		while (i < intArr.length - 1) {
			//System.out.println(intArr[i+=1]);
			// 1, 2, 3, 4, 5, 6 
			System.out.println(intArr[++i]);
		}
		
	}
	
	@Test
	public void test2() {
		int[] intArr = { 0, 1, 2, 3, 4, 5, 6 };
		int i = 0, j = 0;
		while (i < intArr.length) {
			//System.out.println(intArr[i++]);
			// 0, 1, 2, 3, 4, 5, 6
			System.out.println(intArr[j=i++]);
			System.out.println("i=" + i);
			System.out.println("j=" + j);
		}
	}
	
	@Test
	public void test3() {
		int i = 0, j;
		//System.out.println(i = i++); // 0
		System.out.println(j = i++); // 0
		System.out.println(i); // 1
	}
}
