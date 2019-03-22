package org.gmfbilu.superapp.module_view.constraintlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;


/**
 * Created by gmfbilu on 2018/3/21.
 * <p>
 * Guideline 使用的属性
 * Guideline 是 ConstraintLayout 中一个特殊的辅助布局的类。相当于一个不可见的 View，使用 ConstraintLayout 可以创建水平或者垂直的参考线，其他的 View 可以相对于这个参考线来布局
 * 垂直 Guideline 的宽度为 0， 高度为 父容器（ConstraintLayout）的高度.水平 Guideline 的高度为 0， 宽度为 父容器（ConstraintLayout）的宽度
 * 1.android_orientation 控制 Guideline 是横向的还是纵向的
 * 2.layout_constraintGuide_begin 可以指定距离左（或者上）边开始的固定位置
 * 3.layout_constraintGuide_end 可以指定距离右（或者下）边开始的固定位置
 * 4.layout_constraintGuide_percent 可以指定位于布局中所在的百分比，比如距离左边 2% 的位置
 * <p>
 * 相对定位属性
 * 有top,right,bottom,left,baseline，其中baseline是指文字的基准线
 * <p>
 * 居中和偏移(bias)
 * 使用 Bias 可以改变两边的权重（类似于 LinearLayout 中的 weight 属性）
 * 1.layout_constraintHorizontal_bias
 * 2.layout_constraintVertical_bias
 * <p>
 * 控制子 View 的宽高比
 * layout_constraintDimensionRatio 控制子View的宽高比
 * 如果要使用宽高比则需要至少设置一个尺寸约束为 0dp，然后设置 layout_constraintDimentionRatio 属性
 * 比率的取值有两种形式：
 * 1.float 值，代表宽度/高度 的比率
 * 2.“宽度:高度”这种比率值
 * <p>
 * 链条布局（Chains）
 * Chains 为同一个方向（水平或者垂直）上的多个子 View 提供一个类似群组的概念。其他的方向则可以单独控制
 * 创建一个 Chain:
 * 多个 View 相互在同一个方向上双向引用就创建了一个 Chain。什么是双向引用呢？ 比如在水平方向上两个 Button A 和 B，如果 A 的右边位于 B 的左边，而 B 的左边位于 A 的右边，则就是一个双向引用
 * Chain heads:
 * Chain 的属性由该群组的第一个 View 上的属性所控制（第一个 View 被称之为 Chain head）,水平群组，最左边的 View 为 head， 垂直群组最上面的 View 为 head
 * Margins in chains:
 * 可以为 Chain 中的每个子 View 单独设置 Margin。对于 spread chains， 可用的布局空白空间是扣除 margin 后的空间。下面会详细解释
 * Chain Style:
 * 1. layout_constraintHorizontal_chainStyle
 * 2. layout_constraintHorizontal_weight
 * 3. layout_constraintVertical_chainStyle
 * 4. layout_constraintVertical_weight
 * chainStyle 是设置到 Chain Head 上的，指定不同的 style 会改变里面所有 View 的布局方式，有如下四种 Style：
 * 1.CHAIN_SPREAD 这个是默认的 Style， 里面的所有 View 会分散开布局
 * 2.Weighted chain，在 CHAIN_SPREAD 模式下，如果有些 View 的尺寸设置为 MATCH_CONSTRAINT（0dp）,则这些 View 尺寸会占据所有剩余可用的空间，和 LinearLayout weight 类似
 * 3.CHAIN_SPREAD_INSIDE 和 CHAIN_SPREAD 类似，只不过两端的两个 View 和 父容器直接不占用多余空间，多余空间在 子 View 之间分散
 * 4.CHAIN_PACKED 这种模式下，所有的子 View 都 居中聚集在一起，但是可以设置 bias 属性来控制聚集的位置
 * <p>
 * Percent Dimensions
 * 允许我们设置控件占据可用空间的百分比,控件将占据宽度的50%和高度的50%
 * android:layout_width="0dp"
 * android:layout_height="0dp"
 * org.gmfbilu.superapp.lib_base.app:layout_constraintHeight_percent="0.5"
 * org.gmfbilu.superapp.lib_base.app:layout_constraintWidth_percent="0.5"
 * <p>
 * Barrier
 * Barrier是一个虚拟的辅助控件，它可以阻止一个或者多个控件越过自己
 * <p>
 * Group
 * Group帮助你对一组控件进行设置。最常见的情况是控制一组控件的visibility。你只需把控件的id添加到Group，就能同时对里面的所有控件进行操作
 */

public class ConstraintLayoutFragment extends BaseFragment {

    private Toolbar toolbar;
    private Group group;

    public static ConstraintLayoutFragment newInstance() {
        Bundle args = new Bundle();
        ConstraintLayoutFragment fragment = new ConstraintLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        toolbar = view.findViewById(R.id.module_googlelibrary_toolbar);
        group = view.findViewById(R.id.module_googlelibrary_group);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_constraintlayout;
    }

    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        toolbar.setTitle("ConstraintLayout");
        group.setVisibility(View.VISIBLE);

    }


    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
