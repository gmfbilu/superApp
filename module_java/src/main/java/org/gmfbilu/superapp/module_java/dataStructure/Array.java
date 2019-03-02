package org.gmfbilu.superapp.module_java.dataStructure;

/**
 * 数组
 */
public class Array {


    /**
     * 中心索引：数组中心索引的左侧所有元素相加的和等于右侧所有元素相加的和.如果数组不存在中心索引，那么我们应该返回 -1
     * 如果数组有多个中心索引，那么我们应该返回最靠近左边的那一个
     *
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int sumLeft = 0;
            int sumRight = 0;
            for (int j = 0; j < i; j++) {
                sumLeft += nums[j];
            }
            for (int k = i + 1; k < length; k++) {
                sumRight += nums[k];
            }
            if (sumLeft == sumRight) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 在一个给定的数组nums中，总是存在一个最大元素
     * 查找数组中的最大元素是否至少是数组中每个其他数字的两倍
     * 如果是，则返回最大元素的索引，否则返回-1
     *
     * @param nums
     * @return
     */
    public int dominantIndex(int[] nums) {
        int len = nums.length;
        if (len == 1)
            return 0;
        int max = 0;
        for (int i = 1; i < len; i++) {
            if (nums[max] < nums[i])
                max = i;
        }
        for (int i : nums) {
            if (nums[max] < i * 2 && nums[max] != i)
                return -1;
        }
        return max;
    }


    /**
     * 给定一个含有 M x N 个元素的矩阵（M 行，N 列），请以对角线遍历的顺序返回这个矩阵中的所有元素
     *
     * @param matrix
     * @return
     */
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int[] order = new int[m * n];
        int row = 0;
        int col = 0;
        //存储方向改变值，右上，或者左下
        int[][] dirs = {{-1, 1},
                {1, -1}};
        int k = 0;
        for (int i = 0; i < order.length; i++) {
            //将当前坐标赋值给新数组
            order[i] = matrix[row][col];
            //计算下一个点的坐标
            row += dirs[k][0];
            col += dirs[k][1];
            //根据边界条件，修正下一个点的坐标值.触碰边界，必然对方向取反
            //右上方向碰到边界
            if (col > n - 1) {
                col = n - 1;
                row += 2;
                //方向取反
                k = 1 - k;
            }
            if (row < 0) {
                row = 0;
                k = 1 - k;
            }
            //左下方向碰到边界
            if (row > m - 1) {
                row = m - 1;
                col += 2;
                k = 1 - k;
            }
            if (col < 0) {
                col = 0;
                k = 1 - k;
            }
        }
        return order;
    }


}
