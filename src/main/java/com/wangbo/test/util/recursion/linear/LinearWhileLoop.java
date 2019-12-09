package com.wangbo.test.util.recursion.linear;

import java.util.Stack;

/**
 * 所有递归都能改写成循环,有些递归只需要一个循环就可以替代,而有些递归的改写需要循环+栈,即要利用一定的辅助空间记录过程中的某些数据才可以。
 * 所有循环都能改写成递归,都是会不断重复执行相同代码,两者使用不当都会造成死循环.
 * 循环的时间复杂度和空间复杂度都要优于递归，但递归的优越性在于条理清晰，可读性强，比较适宜于问题本身是递归性质的、用循环难于解决的问题。
 * 在二者都不难的情况下，一般都是优先选用循环来解决问题的。
 * 
 * @author 0380008788
 *
 */
public class LinearWhileLoop {
	
	public static void main(String[] args) {
		System.out.println(Fact(5));
	}

	/**
	 * 递归实现求n的阶乘 n! = n * (n-1) * (n-2) *...* 1
	 * @param n
	 * @return
	 */
	static long Fact(long n) {
		if (n < 0)
			return -1;
		if (0 == n)
			return 1;
		else {
			return n * Fact(n - 1);
		}
	}
	
	/**
	  * 求n的阶乘
	 *return 
	  * note result = n! 循环实现
	 */
	/*int FactLoop(long n) {
		// (步骤1) 快照结构体局部声明 
		class SnapShotStruct {
			// 没有局部变量
			long inputN; // 会改变的参数
			int stage; // 阶段变量用于快照跟踪
		}
		// (步骤2)
		int returnVal; // 用于保存当前调用返回值  
		// (步骤3)
		Stack<SnapShotStruct> snapshotStack;
		// (步骤4)
		SnapShotStruct currentSnapshot;
		currentSnapshot.inputN = n;
		currentSnapshot.stage = 0; // 阶段变量初始化
	
		snapshotStack.push(currentSnapshot);
	
		// (步骤5)
		while (!snapshotStack.empty()) {
			currentSnapshot = snapshotStack.top();
			snapshotStack.pop();
	
			// (步骤6)
			switch (currentSnapshot.stage) {
			// (步骤7)
			case 0:
				if (0 > currentSnapshot.inputN) {
					// (步骤8 & 步骤9)
					returnVal = -1;
					continue;
				}
				if (0 == currentSnapshot.inputN) {
					// (步骤8 & 步骤9)
					returnVal = 1;
					continue;
				} else {
					// (步骤10)
	
					// 返回 ( n* Fact(n-1)); 分为2步： 
					// (第一步调用自身，第二步用返回值乘以当前n值)
					// 这里我们拍下快照.
					currentSnapshot.stage = 1; // 当前的快照表示正在被处理，并等待自身调用结果返回，所以赋值为1 
	
					snapshotStack.push(currentSnapshot);
	
					// 创建一个新的快照，用于调用自身
					SnapShotStruct newSnapshot;
					newSnapshot.inputN = currentSnapshot.inputN - 1; // 初始化参数 
	
					newSnapshot.stage = 0; // 从头开始执行自身，所以赋值stage==0 
	
					snapshotStack.push(newSnapshot);
					continue;
	
				}
				break;
			// (步骤7)
			case 1:
	
				// (步骤8)
	
				returnVal = currentSnapshot.inputN * returnVal;
	
				// (步骤9)
				continue;
				break;
			}
		}
	
		// (步骤2)
		return returnVal;
	}
	  */
	/*class SnapShotStruct {
		int n; // - parameter input
		int test; // - local variable that will be used 
		          //     after returning from the function call
		          // - retIdx can be ignored since it is a reference.
		long inputN;      // 会改变的参数
		                          // 没有局部变量
		int stage; // - Since there is process needed to be done 
		           //     after recursive call. (Sixth rule)
	};*/
}
