package com.wangbo.test.util.sort.integer;

import java.util.Arrays;

/**
 * 归并排序（递归）
 *
 * ①. 将序列每相邻两个数字进行归并操作，形成 floor(n/2)个序列，排序后每个序列包含两个元素；
 * ②. 将上述序列再次归并，形成 floor(n/4)个序列，每个序列包含四个元素；
 * ③. 重复步骤②，直到所有元素排序完毕。
 * 
 * 注意:
 * 1. 通过自上而下的递归实现的归并排序, 将存在堆栈溢出的风险
 * 2. 归并排序是稳定的排序算法, 保证排序后值相等的时候两个元素的相对位置不变
 */
public class MergeArithmetic {
	
	public static void main(String[] args) {
		int[] arr = {4,8,6,2,3,5,1,2,3,8,7};
//		System.out.println(Arrays.toString(mergeSort1(arr)));
		
		mergeSort2(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static int[] mergeSort1(int[] arr) {
		if (arr.length <= 1)
			return arr;
		
		int mid = arr.length >> 1;
		int[] leftArr = Arrays.copyOfRange(arr, 0, mid);
		int[] rightArr = Arrays.copyOfRange(arr, mid, arr.length);
//		System.out.println(
//				"split two array: " + Arrays.toString(leftArr) + " And " + Arrays.toString(rightArr));
		//不断拆分为最小单元，再排序合并
		return mergeTwoSortedArr(mergeSort1(leftArr), mergeSort1(rightArr));
	}
	
	public static int[] mergeTwoSortedArr(int[] arr1, int[] arr2) {
		int[] mergeResult = new int[arr1.length + arr2.length];
		int i = 0, j = 0, k = 0;
		while (i < arr1.length && j < arr2.length) {
			if (arr1[i] <= arr2[j]) {
				mergeResult[k++] = arr1[i++];
			} else {
				mergeResult[k++] = arr2[j++];
			}
		}
		while (i < arr1.length) {
			mergeResult[k++] = arr1[i++];
		}
		while (j < arr2.length) {
			mergeResult[k++] = arr2[j++];
		}
		return mergeResult;
	}

	public static void mergeSort2(int[] arr) {
		mergeSort3(arr, 0, arr.length - 1);
	}
	
	/**
	 * @param arr
	 * @param startIndex	include
	 * @param endIndex    	include
	 */
	public static void mergeSort3(int[] arr, int startIndex, int endIndex) {
		if (startIndex >= endIndex)
			return;
		int midIndex = startIndex + ((endIndex - startIndex) >> 1);
		mergeSort3(arr, startIndex, midIndex);
		mergeSort3(arr, midIndex + 1, endIndex);
		
		mergeSortedArr(arr, startIndex, midIndex, endIndex);
	}
	
	/**
	 * @param arr
	 * @param startIndex	include
	 * @param midIndex		include
	 * @param endIndex    	include
	 */
	public static void mergeSortedArr(int[] arr, int startIndex, int midIndex, int endIndex) {
		int[] temp = new int[endIndex - startIndex + 1];
		int i = startIndex, j = midIndex + 1, k = 0;
		while (i <= midIndex && j <= endIndex) {
			if (arr[i] <= arr[j]) {
				temp[k++] = arr[i++];
			} else {
				temp[k++] = arr[j++];
			}
		}
		while (i <= midIndex) {
			temp[k++] = arr[i++];
		}
		while (j <= endIndex) {
			temp[k++] = arr[j++];
		}
		for (i = 0; i < temp.length; i++) {
			arr[startIndex + i] = temp[i];
		}
	}
}

