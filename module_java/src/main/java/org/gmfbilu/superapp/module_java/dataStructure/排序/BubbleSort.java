package org.gmfbilu.superapp.module_java.dataStructure.排序;

/**
 * 冒泡排序
 * <p>
 * 基本思想：两个数比较大小，较小的数下沉排在前面，较大的数冒起来排在后面
 * <p>
 * 过程：
 * 1.比较相邻的两个数据，如果第二个数小，就交换位置，第一轮比较后最大的数在最后(比较n-1)，第二轮比较后第二大的数在倒数第二(n-2)
 * <p>
 * 平均时间复杂度：O(n2)
 */
public class BubbleSort {

    public static void BubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {   //表示趟数，一共arr.length-1次。
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] + arr[j + 1];
                    arr[j + 1] = arr[j] - arr[j + 1];
                    arr[j] = arr[j] - arr[j + 1];
                }
            }
        }
    }

    /**
     * 设置标志位flag，如果发生了交换flag设置为true；如果没有交换就设置为false
     * 这样当一轮比较结束后如果flag仍为false，即：这一轮没有发生交换，说明数据的顺序已经排好，没有必要继续进行下去
     */
    public static void BubbleSort1(int[] arr) {
        boolean flag;//是否交换的标志
        for (int i = 0; i < arr.length - 1; i++) {   //表示趟数，一共arr.length-1次。
            flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    arr[j] = arr[j] + arr[j + 1];
                    arr[j + 1] = arr[j] - arr[j + 1];
                    arr[j] = arr[j] - arr[j + 1];
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

}
