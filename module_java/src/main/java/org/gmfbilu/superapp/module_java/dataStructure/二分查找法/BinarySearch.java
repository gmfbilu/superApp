package org.gmfbilu.superapp.module_java.dataStructure.二分查找法;

/**
 * 现在我们来玩一个猜数的游戏，假设有一个人要我们猜0-99之间的一个数。那么最好的方法就是从0-99的中间数49开始猜。如果要猜的数小于49，就猜24（0-48的中间数）；如果要猜的数大于49，就猜74（50-99的中间数）。重复这个过程来缩小猜测的范围，直到猜出正确的数字。二分查找的工作方法类似于此
 * <p>
 * 二分查找操作的数据集是一个有序的数据集。开始时，先找出有序集合中间的那个元素。如果此元素比要查找的元素大，就接着在较小的一个半区进行查找；反之，如果此元素比要找的元素小，就在较大的一个半区进行查找。在每个更小的数据集中重复这个查找过程，直到找到要查找的元素或者数据集不能再分割
 * <p>
 * 二分查找能应用于任何类型的数据，只要能将这些数据按照某种规则进行排序。然而，正因为它依赖于一个有序的集合，这使得它在处理那些频繁插入和删除操作的数据集时不太高效。
 * 当待搜索的集合是相对静态的数据集时，此时使用二分查找是最好的选择.
 * <p>
 * 二分查找法实质上是不断地将有序数据集进行对半分割，并检查每个分区的中间元素
 */
public class BinarySearch {

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int[] arr = {0, 10, 11, 14, 18};
        binarySearch.binarySearch(arr, 18);
        int i = 1;
        System.out.println((100 + i++) + "");
    }


    /**
     * 查找条件：顺序存储，而且是从小到大排列好顺序
     */
    private int binarySearch(int[] arr, int key) {
        int left = 0;
        int right = arr.length - 1;
        int index = (left + right) / 2;
        int middle = arr[index];
        while (key != middle) {
            if (key < middle) {
                right = index - 1;
            } else {
                left = index + 1;
            }
            index = (left + right) / 2;
            middle = arr[index];
            //这判断 == 就可以了，因为不会出现left > right
            if (left == right) {
                if (key != middle) {
                    index = -1;
                }
                break;
            }
        }
        System.out.println(index + "");
        return index;
    }

}
