package com.wangbo.test.util.recursion.linear;

import java.util.Stack;

/**
 * 求函数式A（m,n）代入n时的值
 * A(m,n) = n + 1 			当m=0时
 * 		  = A(m-1,1)		当m>0且n=0时
 * 		  = A(m-1,A(m,n-1))	当m>0且n>0
 * @author 0380008788
 */
public class LinkedStack {
	// http://blog.sina.com.cn/s/blog_642c9bdd0101793z.html
	public static void main(String[] args) {
		//java.lang.StackOverflowError
		System.out.println(Ackerman(3,12));
		System.out.println(stackArithmeticOfWhileLoop(3,12));
	}
	
	public static int Ackerman(int m, int n) {
		if (m == 0) {
			return n + 1;
		} else if (m > 0 && n == 0) {
			return Ackerman(m - 1, 1);
		} else if (m > 0 && n > 0) {
			return Ackerman(m - 1, Ackerman(m, n - 1));
		}
		return -Integer.MIN_VALUE;
	}
	
	/**
	 * 	在一般情况下，将递归算法转化成等价的非递归算法应该设置的数据结构为栈。
	 *	分析：使用递归算法时分情况将不同的(m,n)压栈进行算，并且有的A（m,n）计算依赖于上一次弹栈计算的值
	 *	因此
	 *	1.我们需要将在栈中记录两个变量值，需要采用链性栈，
	 *	2.有的n值没有具体值，需要通过上一次弹栈计算得出，这种情况的n需要用一个特殊值（这里用-1代替）来代替：表明这个弹栈出的n值等于上一次弹栈计算结果
	 *  3.压栈的m、n值直到m==0时就到了栈的最顶层
	 *  
	 *  用stack来模拟java程序中的压栈弹栈运算过程
	 */
	public static int stackArithmeticOfWhileLoop (int m, int n) {
		int result = 0;
		int Xresult;
		Stack<Integer> varStack = new Stack<Integer>();
		varStack.push(m);
		varStack.push(n);
		while (!varStack.empty()) {
			n = varStack.pop();
			m = varStack.pop();
			//akm(m,n)=n+1
			if (m == 0 && n >= 0) {
				Xresult = n + 1;
				if (!varStack.empty()) {
					n = varStack.peek(); 
					if (n < 0) {
						varStack.pop();
						varStack.push(Xresult);
					}
				} else {
					result = Xresult;
					break;
				}
			// akm(m,n)=akm(m-1,1)
			} else if (m > 0 && n == 0) {
				varStack.push(m - 1);
				varStack.push(1);
			// akm(m,n)=akm(m - 1, akm(m, n - 1))
			} else if (m > 0 && n > 0) {
				varStack.push(m - 1);
				varStack.push(-1);

				varStack.push(m);
				varStack.push(n - 1);
			}
		}
		return result;
	}
}
