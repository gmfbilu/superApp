package org.gmfbilu.superapp.module_java.basic.循环遍历;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class LoopTraversal {

    public static void main(String[] args) {

    }

    /**
     * 循环
     * ArrayList通过get(index)来获取对象，是这三种遍历方式中获取对象最快的方式，For Each的效率最差
     * For each其实也是用了迭代器来实现，因此当数据量变大时，两者的效率基本一致。foreach 写法实际上是对的 Iterable、hasNext、next方法的简写
     * ArrayList是通过动态数组来实现的，支持随机访问，所以get(index)是很快的。迭代器，其实也是通过数组名+下标来获取，而且增加了逻辑，自然会比get(index)慢
     * <p>
     * 对于ArrayList和LinkedList，在size小于1000时，每种方式的差距都在几ms之间，差别不大，选择哪个方式都可以
     * 对于ArrayList，无论size是多大，差距都不大，选择哪个方式都可以
     * 对于LinkedList，当size较大时，建议使用迭代器或for-each的方式进行遍历，效率会有较明显的差距
     */
    public static void arrayList1() {
        ArrayList<LoopTraversalBean> loopTraversalBeans = new ArrayList<>();
        for (int i = 0; i < loopTraversalBeans.size(); i++) {
            LoopTraversalBean loopTraversalBean = loopTraversalBeans.get(i);
            if (loopTraversalBean.name.equals("messi")) {
                /*
                 * 错误做法
                 * List 删除元素的逻辑是将目标元素之后的元素往前移一个索引位置，最后一个元素置为 null，同时 size - 1
                 * loopTraversalBeans.remove(loopTraversalBean)
                 */
            }
        }

        /**
         * 安全删除元素的做法
         * for (int i = loopTraversalBeans.size() - 1; i >= 0; i--) {
         *             LoopTraversalBean loopTraversalBean = loopTraversalBeans.get(i);
         *             if (loopTraversalBean.name.equals("messi")) {
         *                 loopTraversalBeans.remove(loopTraversalBean);
         *             }
         *         }
         */

    }

    /**
     * for each
     */
    public static void arrayList2() {
        ArrayList<LoopTraversalBean> loopTraversalBeans = new ArrayList<>();
        for (LoopTraversalBean loopTraversalBean : loopTraversalBeans) {
            if (loopTraversalBean.name.equals("messi")) {
                /*
                 * 错误做法
                 * loopTraversalBeans.remove(loopTraversalBean)
                 */
            }
        }
        /**
         * 安全删除元素的for each
         * CopyOnWriteArrayList 解决了 List的并发问题
         * CopyOnWriteArrayList<LoopTraversalBean> loopTraversalBeans1 = new CopyOnWriteArrayList<>();
         *          for (LoopTraversalBean loopTraversalBean : loopTraversalBeans1) {
         *              if (loopTraversalBean.name.equals("messi")) {
         *                  //安全删除元素
         *                  loopTraversalBeans1.remove(loopTraversalBean);
         *             }
         *          }
         */
    }

    /**
     * iterator
     */
    public static void arrayList3() {
        ArrayList<LoopTraversalBean> loopTraversalBeans = new ArrayList<>();
        Iterator<LoopTraversalBean> iterator = loopTraversalBeans.iterator();
        //while是循环结构，if是选择结构
        while (iterator.hasNext()) {
            LoopTraversalBean loopTraversalBean = iterator.next();
            if (loopTraversalBean.name.equals("messi")) {
                //安全删除元素
                iterator.remove();
                /*
                 * 错误做法
                 * loopTraversalBeans.remove(loopTraversalBean)
                 */

            }
        }
    }

    /**
     * LinkedList通过get(index)来获取对象，是这三种遍历方式中获取对象最慢的方式，iterator是最快的，而且数据量越大get(index)越慢
     * LinkedList是通过双向链表实现的，无法支持随机访问。当你要向一个链表取第index个元素时，它需要二分后从某一端开始找，一个一个地数才能找到该元素
     * 而迭代器提供的是获取下一个的方法，时间复杂度为O(1)，所以会比较快
     */
    public static void linkedList1() {
        LinkedList<LoopTraversalBean> loopTraversalBeans = new LinkedList<>();
        for (int i = 0; i < loopTraversalBeans.size(); i++) {
            LoopTraversalBean loopTraversalBean = loopTraversalBeans.get(i);
            if (loopTraversalBean.name.equals("messi")) {

            }
        }
    }

    public static void linkedList2() {
        LinkedList<LoopTraversalBean> loopTraversalBeans = new LinkedList<>();
        for (LoopTraversalBean loopTraversalBean : loopTraversalBeans) {
            if (loopTraversalBean.name.equals("messi")) {

            }
        }
    }

    public static void linkedList3() {
        LinkedList<LoopTraversalBean> loopTraversalBeans = new LinkedList<>();
        Iterator<LoopTraversalBean> iterator = loopTraversalBeans.iterator();
        while (iterator.hasNext()) {
            LoopTraversalBean loopTraversalBean = iterator.next();
            if (loopTraversalBean.name.equals("messi")) {

            }
        }
    }

    /**
     * iterator，这种最快，也可以边遍历边删除元素
     */
    public static void hashMap() {
        HashMap<String, LoopTraversalBean> stringLoopTraversalBeanMap = new HashMap<>();
        Iterator<Map.Entry<String, LoopTraversalBean>> iterator = stringLoopTraversalBeanMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, LoopTraversalBean> next = iterator.next();
            LoopTraversalBean value = next.getValue();
            if (value.name.equals("messi")) {

            }
        }
    }

}
