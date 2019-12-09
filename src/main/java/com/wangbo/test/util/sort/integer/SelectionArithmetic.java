package com.wangbo.test.util.sort.integer;

import java.util.Arrays;

/**
 * 选择排序
 *
 * 1. 从待排序序列中，找到关键字最小的元素；
 * 2. 如果最小元素不是待排序序列的第一个元素，将其和第一个元素互换；
 * 3. 从余下的 N - 1 个元素中，找出关键字最小的元素，重复①、②步，直到排序结束。
 *    仅增量因子为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 *    
 *    选择排序是不稳定的排序算法, 不能保证排序后值相等的时候两个元素的相对位置不变
 *    每次交换的元素都有可能不是相邻的, 因此它有可能打破原来值为相同的元素之间的顺序
 */
public class SelectionArithmetic {
	
	public static void main(String[] args) {
		int[] arr = { 3, 8, 6, 3, 2, 5, 1, 2, 4, 8, 7 };
		selectSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void selectSort(int[] arr) {
		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[j] < arr[minIndex]) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				int temp = arr[minIndex];
				arr[minIndex] = arr[i];
				arr[i] = temp;
			}
		}
	}
}
