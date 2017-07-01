
public class Sort {
	
	public static void main(String[] args) {
		selectionSort();
		System.out.print("\n");
		bubbleSort();
		System.out.print("\n");
		perpare();
		System.out.print("\n");
		mergeSort();
	}
	//选择排序  O(n^2)
	public static void selectionSort() {
		int[] array = {99,11,14,23,56,54,34,25};
		int length = array.length;
		int inter;
		for(int k=0;k<length-1;k++) { // n-1
			for(int i=k;i<length;i++) { // n(n-1)
				if(array[i] < array[k]) {
					inter = array[k];
					array[k] = array[i];
					array[i] = inter;
				}
			}
		}
		
		for(int i=0;i<array.length ;i++) {
			System.out.print(array[i]+" ");
		}
		
	}
	
	//冒泡排序 O(n^2)
	public static void bubbleSort() {
		int[] array = {99,11,14,23,56,54,34,25};
		int length = array.length;
		int inter;
		for(int i=0;i<length;i++) { // n
			for(int k=i+1;k<length;k++) { // n(n-1)
				if(array[k] < array[i]) {
					inter = array[i];
					array[i] = array[k];
					array[k] = inter;
				}
			}
		}
		
		for(int i=0;i<array.length ;i++) {
			System.out.print(array[i]+" ");
		}
	}
	//快速排序
	public static void perpare() {
		int[] array = {99,11,14,23,56,54,34,25};
		int left =0; 
		int right = array.length - 1;
		
		quickSort(array,left,right);
		
		for(int i=0;i<array.length ;i++) {
			System.out.print(array[i]+" ");
		}
	}
	public static void quickSort(int[] array,int left,int right) {
		/*
		 * 取一个数组的关键数key(一般为第一位),从右开始将比key小的数与key交换,从左开始将比key大的数与key交换
		 * 一轮排序过后将比key小的数都放到数组的左边,比key大的数都放到数组的右边,然后将左右两边看成两个数组重新进行上一步操作
		 * */
		if(left < right) {
			int i = left;
			int j = right;
			int key = array[i];
			while(i<j) {
				while(i < j && array[j] >= key) {
					j--;
				}
				if(i < j) {
					array[i] = array[j];
				}
				while(i < j && array[i] < key) {
					i++;
				}
				if(i < j) {
					array[j] = array[i];
				}
			}
			array[i] = key;
			quickSort(array,left,i);
			quickSort(array,i+1,right);
		}
		
	}
	//归并排序
	public static void mergeSort() {
		/*
		 * 将一个数组分割成多个小数组进行排序(最开始都分成两个一组的小数组进行排序),然后再两两合并排序,最后合并成有序的数组
		 * 第一个for循环用于定义数组怎么分解与合并(即分解后小数组的长度)
		 * 第二个for循环用于将分解的小数组进行合并
		 * i:小数组最后一位的下标
		 * j:小数组第一位的下标
		 * merge()中的四个参数为:数组,第一个小数组的第一个元素的下标,第一个小数组的最后一个元素的下标,第二个小数组最后一位的下标
		 * */
		int[] array = {99,11,14,23,56,54,34,25};
		for(int i=1;i<array.length;i=2*i) {
			int j;
			for(j=0;j+2*i-1<array.length;j=j+i*2) {
				merge(array,j,j+i-1,j+2*i-1);
			}
			//如果最后一组合并的两个小数组长度不一样,即排序的数组长度为奇数
			if(j+i-1<array.length) {
				merge(array,j,j+i-1,array.length-1);
			}
		}
		for(int i=0;i<array.length ;i++) {
			System.out.print(array[i]+" ");
		}
	}
	//合并两个小数组
	public static void merge(int[] array, int first, int second, int k) {
		/*
		 * i:第一个小数组的第一个元素的下标
		 * j:第二个小数组的第一个元素的下标
		 * k:第二个小数组的最后一个元素的下标
		 * temp:用来暂时存放合并后的两个小数组
		 * */
		int i = first; 
		int j = second+1; 
		int[] temp = new int[k-i+1]; 
		System.out.println(i+" "+j+" "+k);
		int l = 0;
		//将两个小数组中的元素按大小排序并合并
		while(i <= second && j <= k) { 
			if(array[i] <= array[j]) {
				temp[l] = array[i];
				i++;
				l++;
			} else {
				temp[l] = array[j];
				j++;
				l++;
			}
		}
		while(i <= second) {
			temp[l] = array[i];
			i++;
			l++;
		}
		while(j <= k) {
			temp[l] = array[j];
			j++;
			l++;
		}
		for(l=0,i=first;i<=k;l++,i++) {
			array[i] = temp[l];
		}
	}
	
	//深度优先搜索
	//广度优先搜索
	//预排序
	//AVL树
	//堆排序
	//平衡树: 2-3 tree
}