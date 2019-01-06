package org.gmfbilu.superapp.module_view.shape;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;


/**
 * android:shape=["rectangle"|"oval"|"line"|"ring"]
 * shape的形状，默认为矩形，可以设置为矩形（rectangle）、椭圆形(oval)、线(line)、环形(ring)
 * <p>
 * 下面的属性只有在android:shape="ring时可用：
 * android:innerRadius 内环的半径。
 * android:innerRadiusRatio 浮点型，以环的宽度比率来表示内环的半径，
 * 例如，如果android:innerRadiusRatio，表示内环半径等于环的宽度除以5，这个值是可以被覆盖的，默认为9.
 * android:thickness 环的厚度
 * android:thicknessRatio 浮点型，以环的宽度比率来表示环的厚度，例如，如果android:thicknessRatio="2"，
 * 那么环的厚度就等于环的宽度除以2。这个值是可以被android:thickness覆盖的，默认值是3.
 * android:useLevel boolean值，如果当做是LevelListDrawable使用时值为true，否则为false.
 * <p>
 * android:width   整型 宽度
 * android:height  整型 高度
 * <--圆角
 * android:radius              　　　   整型 半径
 * android:topLeftRadius       　　  整型 左上角半径
 * android:topRightRadius               整型 右上角半径
 * android:bottomLeftRadius           整型 左下角半径
 * android:bottomRightRadius         整型 右下角半径
 * -->
 * <corners android:radius="10dp"/><!-- 设置圆角半径，可以分别设置4个角 -->
 * <p>
 * <--渐变，这个设置之后一般就不要设置solid填充色了
 * android:startColor  颜色值     起始颜色
 * android:endColor    颜色值     结束颜色
 * android:centerColor 整型       渐变中间颜色，即开始颜色与结束颜色之间的颜色
 * android:angle       整型       渐变角度(PS：当angle=0时，渐变色是从左向右。 然后逆时针方向转，当angle=90时为从下往上。angle必须为45的整数倍)
 * android:type        ["linear" | "radial" | "sweep"] 渐变类型(取值：linear、radial、sweep)
 * linear 线性渐变，这是默认设置
 * radial 放射性渐变，以开始色为中心。
 * sweep 扫描线式的渐变。
 * android:useLevel     ["true" | "false"]如果要使用LevelListDrawable对象，就要设置为true。设置为true无渐变。false有渐变色
 * android:gradientRadius 整型    渐变色半径.当 android:type="radial" 时才使用。单独使用 android:type="radial"会报错。
 * android:centerX      整型     渐变中心X点坐标的相对位置
 * android:centerY      整型     渐变中心Y点坐标的相对位置
 * -->
 * <-- 间隔
 * 内边距，即内容与边的距离
 * android:left        整型 左内边距
 * android:top        整型 上内边距
 * android:right      整型 右内边距
 * android:bottom   整型 下内边距
 * -->
 * <--填充
 * android:color   颜色值 填充颜色
 * -->
 * <--描边
 * android:width          整型      描边的宽度
 * android:color           颜色值    描边的颜色
 * android:dashWidth   整型      表示描边的样式是虚线的宽度， 值为0时，表示为实线。值大于0则为虚线。
 * android:dashGap     整型      表示描边为虚线时，虚线之间的间隔 即“ - - - - ”
 * -->
 * <p>
 * shape 可以定义四种类型的几何图形，由 android:shape 属性指定
 *   line --> 线
 *   rectangle --> 矩形（圆角矩形）
 *   oval --> 椭圆，圆
 *   ring --> 圆环
 * <p>
 * shape 可以定义边框属性
 *   有边框，无边框，虚线边框，实线边框
 * <p>
 * shape 可以实现矩形圆角效果
 *   可以指定其中一个角或者多个角设置圆角效果
 *   指定圆角半径设置圆角的大小
 * <p>
 * shape 可以实现三种渐变，由子标签 gradient 实现
 *   linear --> 线性渐变（水平，垂直，对角线三个渐变）
 *   sweep --> 扫描渐变（只支持顺时针方向，其实颜色反过来就跟逆时针一样的了）
 *   radial --> 径向渐变（由指定的中心点开始向外渐变，指定半径）
 *   xml 实现只支持三个颜色，startColor，CenterColor，endColor
 */
public class ShapeFragment extends BaseFragment {

    public static ShapeFragment newInstance() {
        Bundle args = new Bundle();
        ShapeFragment fragment = new ShapeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_shape;
    }

    @Override
    public void onClick(View v) {

    }
}
