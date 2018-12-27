package org.gmfbilu.superapp.module_java.sort;

/**
 * 选择排序
 * <p>
 * 基本思想：
 * 在长度为N的无序数组中，第一次遍历n-1个数，找到最小的数值与第一个元素交换；
 * 第二次遍历n-2个数，找到最小的数值与第二个元素交换
 * ...
 * 第n-1次遍历，找到最小的数值与第n-1个元素交换，排序完成
 * <p>
 * 平均时间复杂度：O(n2)
 */
public class SelctionSort {

    public static void select_sort(int array[], int lenth) {

        for (int i = 0; i < lenth - 1; i++) {

            int minIndex = i;
            for (int j = i + 1; j < lenth; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
            }
        }
    }

}
