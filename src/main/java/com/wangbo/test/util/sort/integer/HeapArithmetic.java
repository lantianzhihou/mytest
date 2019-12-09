package com.wangbo.test.util.sort.integer;

import java.util.Arrays;

/**
 * 	堆排序
 * 	堆的定义如下：n个元素的序列{k1,k2,···,kn}，当且仅当满足下关系时，称之为堆。
 * 		（小顶堆）ki <= k(2i)  且   ki <= k(2i+1) 或： 
 * 		（大顶堆）ki >= k(2i) 且 ki >= k(2i+1)
 * 	对于数组的堆节点（数组索引i）的访问：
 * 		父节点i的左子节点在位置：(2*i+1);
 * 		父节点i的右子节点在位置：(2*i+21);
 * 		子节点i的父节点在位置：floor((i-1)/2);
 * 
 * 	1. 先将初始序列K[1..n]建成一个大顶堆, 那么此时第一个元素K1最大, 此堆为初始的无序区.
 * 	2. 再将关键字最大的记录K1 (即堆顶, 第一个元素)和无序区的最后一个记录 Kn 交换, 由此得到新的无序区K[1..n−1]和有序区K[n], 且满足K[1..n−1].keys⩽K[n].key
 * 	3. 交换K1 和 Kn 后, 堆顶可能违反堆性质, 因此需将K[1..n−1]调整为堆. 然后重复步骤②, 直到无序区只有一个元素时停止.
 * 
 * 	堆排序是不稳定的排序算法
 */
public class HeapArithmetic {
	
	public static void main(String[] args) {
		int[] arr = {4,8,6,2,3,5,1,2,3,8,7};
		heapSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	public static void heapSort(int[] arr) {
		for (int i = arr.length - 1; i >= 0; i--) {
			max_heapify(arr, 0, i);
			//堆顶元素(第一个元素)与Kn交换
			int temp = arr[0];      
	        arr[0] = arr[i];
	        arr[i] = temp;
		}
	}
	
	// 建立大顶堆
	public static void max_heapify(int[] arr, int startIndex, int endIndex){
		if (startIndex >= endIndex || startIndex < 0 || arr.length - 1 < endIndex)
			return;
		int parentIdx = (endIndex - startIndex + 1) / 2 - 1;

	    for(; parentIdx >= 0; parentIdx--){
			if (parentIdx * 2 + 1 > endIndex) {
				continue;
			}
			//左子节点位置
	        int left = parentIdx * 2 + 1;       
	        //右子节点位置，如果没有右节点，默认为左节点位置
	        int right = (left + 1) > endIndex ? left : (left + 1);    
	        //交换父节点与左右子节点中的最大值
	        int maxChildId = arr[left] >= arr[right] ? left : right;
	        if(arr[maxChildId] > arr[parentIdx]){   //交换父节点与左右子节点中的最大值
	            int temp = arr[parentIdx];
	            arr[parentIdx] = arr[maxChildId];
	            arr[maxChildId] = temp;
	        }
	    }
	    System.out.println("Max_Heapify: " + Arrays.toString(arr));
	}
	
	// 建立小顶堆
	public static void min_heapify(int[] arr, int startIndex, int endIndex){
		if (startIndex >= endIndex || startIndex < 0 || arr.length - 1 < endIndex)
			return;
	    int parentIdx = (endIndex - startIndex + 1)/ 2;

	    for(; parentIdx >= 0; parentIdx--){
			if (parentIdx * 2 + 1 > endIndex) {
				continue;
			}
			//左子节点位置
	        int left = parentIdx * 2 + 1;       
	        //右子节点位置，如果没有右节点，默认为左节点位置
	        int right = (left + 1) > endIndex ? left : (left + 1);    
	        //交换父节点与左右子节点中的最大值
	        int minChildId = arr[left] < arr[right] ? left : right;
	        if(arr[minChildId] < arr[parentIdx]){   //交换父节点与左右子节点中的最大值
	            int temp = arr[parentIdx];
	            arr[parentIdx] = arr[minChildId];
	            arr[minChildId] = temp;
	        }
	    }
	    System.out.println("Min_Heapify: " + Arrays.toString(arr));
	}
}
