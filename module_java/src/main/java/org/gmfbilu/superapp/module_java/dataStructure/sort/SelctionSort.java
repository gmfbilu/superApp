package org.gmfbilu.superapp.module_java.dataStructure.sort;

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

    public static void main(String[] arge) {
        int array[] = {5, 3, 6, 8, 1, 7, 9, 4, 2};
        select_sort(array);
    }


    static void select_sort(int array[]) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            //找出下列循环最小的数与array[i]比较
            int minIndex = i;
            for (int j = i + 1; j < length; j++) {
                minIndex = array[j] < array[minIndex] ? j : minIndex;
            }
            //如果小就交换位置
            if (array[i] > array[minIndex]) {
                int tem = array[i];
                array[i] = array[minIndex];
                array[minIndex] = tem;
            }
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

}
