package com.wangbo.test.util.sort.integer;

import java.util.Arrays;
import java.util.Stack;

/**
 * ①. 从数列中挑出一个元素，称为”基准”（pivot）。
 * ②.重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。
 * 在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
 * ③. 递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 *
 * 通常以“三者取中法”来选取基准记录，即将排序区间的两个端点与中点三个记录关键码居中的调整为支点记录。快速排序是一个不稳定的排序方法
 * 快速排序每次交换的元素都有可能不是相邻的, 因此它有可能打破原来值为相同的元素之间的顺序. 因此, 快速排序并不稳定
 */
public class QuicklyArithmetic {

	public static void main(String[] args) {
		int[] arr = { 4, 8, 6, 2, 3, 5, 1, 2, 3, 8, 7 };
		quicklySort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 递归版的快速排序
	 */
	public static void quicklySort(int[] arr, int low, int high) {
		if (arr.length <= 1)
			return;
		if (low >= high || high >= arr.length || low < 0)
			return;
		int left = low, right = high;
		int temp = arr[left];
		while (left < right) {
			while (left < right && arr[right] >= temp) {
				right--;
			}
			arr[left] = arr[right];
			while (left < right && arr[left] <= temp) {
				left++;
			}
			arr[right] = arr[left];
		}
		//基准值填补到坑3中，准备分治递归快排
		arr[left] = temp;   
		System.out.println("temp=" + temp + ";Sorting: " + Arrays.toString(arr));
		quicklySort(arr, low, left-1);
		quicklySort(arr, left+1, high);
	}
	
	/**
	 * 快速排序（非递归）
	 *
	 * ①. 从数列中挑出一个元素，称为"基准"（pivot）。
	 * ②. 重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任一边）。在这个分区结束之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
	 * ③. 把分区之后两个区间的边界（low和high）压入栈保存，并循环①、②步骤
	 * @param arr   待排序数组
	 */
	public static void quickSortByStack(int[] arr){
	    if(arr.length <= 0) return;
	    Stack<Integer> stack = new Stack<Integer>();

	    //初始状态的左右指针入栈
	    stack.push(0);
	    stack.push(arr.length - 1);
	    while(!stack.isEmpty()){
	        int high = stack.pop();     //出栈进行划分
	        int low = stack.pop();

	        int pivotIdx = partition(arr, low, high);

	        //保存中间变量
			if (pivotIdx - 1 > low) {
				stack.push(low);
				stack.push(pivotIdx - 1);
			}
			if (pivotIdx + 1 < high && pivotIdx >= 0) {
				stack.push(pivotIdx + 1);
				stack.push(high);
			}
	    }
	}

	private static int partition(int[] arr, int low, int high){
	    if(arr.length <= 0) return -1;
	    if(low >= high) return -1;
	    int l = low;
	    int r = high;

	    //挖坑1：保存基准的值
	    int pivot = arr[l];    
	    while(l < r){
	    	//坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
	        while(l < r && arr[r] >= pivot){  
	            r--;
	        }
	        arr[l] = arr[r];
	        //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
	        while(l < r && arr[l] <= pivot){   
	            l++;
	        }
	        arr[r] = arr[l];
	    }
	    //基准值填补到坑3中，准备分治递归快排
	    arr[l] = pivot;   
	    return l;
	}
}
