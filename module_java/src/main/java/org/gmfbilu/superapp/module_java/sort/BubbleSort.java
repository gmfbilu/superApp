package org.gmfbilu.superapp.module_java.sort;

/**
 * 冒泡排序
 *
 * 基本思想：两个数比较大小，较大的数下沉，较小的数冒起来
 *
 * 过程：
 * 1.比较相邻的两个数据，如果第二个数小，就交换位置。
 * 2.从后向前两两比较，一直到比较最前两个数据。最终最小数被交换到起始的位置，这样第一个最小数的位置就排好了。
 * 3.继续重复上述过程，依次将第2.3...n-1个最小数排好位置
 *
 * 平均时间复杂度：O(n2)
 */
public class BubbleSort {

    public static void BubbleSort(int[] arr) {

        int temp;//临时变量
        for (int i = 0; i < arr.length - 1; i++) {   //表示趟数，一共arr.length-1次。
            for (int j = arr.length - 1; j > i; j--) {

                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

    /**
     * 设置标志位flag，如果发生了交换flag设置为true；如果没有交换就设置为false
     * 这样当一轮比较结束后如果flag仍为false，即：这一轮没有发生交换，说明数据的顺序已经排好，没有必要继续进行下去
     */
    public static void BubbleSort1(int[] arr) {

        int temp;//临时变量
        boolean flag;//是否交换的标志
        for (int i = 0; i < arr.length - 1; i++) {   //表示趟数，一共arr.length-1次。

            flag = false;
            for (int j = arr.length - 1; j > i; j--) {

                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

}
