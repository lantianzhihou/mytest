package com.wangbo.test.util.recursion.linear;

import java.util.Stack;

/**
 * 求函数式F（n）代入n时的值
 * F(n) = 1 		当n=0时
 * 		= n*F(n/2)	当n>0时
 * @author 0380008788
 */
public class LinearStack {
	
	public static void main(String[] args) {
		System.out.println(recursionArithmetic(20));
		System.out.println(stackArithmeticOfWhileLoop(20));
	}
	
	public static int recursionArithmetic(int n) {
		if (n == 0) {
			return 1;
		} else if (n > 0) {
			return n * recursionArithmetic(n / 2);
		}
		return -1;
	}
	
	/**
	 * 	在一般情况下，将递归算法转化成等价的非递归算法应该设置的数据结构为栈。
	 *	分析：使用递归算法时依次将F(n)、F(n/2)、F(n/2/2)、...、F(0)压栈进行计算 
	 *		即依次将变量n、n/2、n/2/2、...、1、0压栈进行运算
	 *	因此我们仅需要将在栈中记录变量值:
	 *	每次弹栈的变量的函数计算结果=前次弹栈变量的函数计算值*n
	 */
	public static int stackArithmeticOfWhileLoop (int n) {
		Stack<Integer> varStack = new Stack<Integer>();
		for (; n >= 0; n = n / 2) {
			varStack.push(n);
			if (n == 0)
				break;
		}
		int result = -1;
		while (!varStack.empty()) {
			Integer varInt = varStack.pop();
			if (varInt == 0) {
				result = 1;
			} else if (varInt > 0) {
				result = varInt * result;
			}
		}
		return result;
	}
}
