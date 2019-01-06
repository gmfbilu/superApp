package org.gmfbilu.superapp.module_util.cartoon;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

/**
 * https://github.com/OCNYang/Android-Animation-Set/tree/master/property-animation
 * 属性动画,属性动画实现原理就是修改控件的属性值实现的动画
 * <p>
 * <p>
 * 在使用属性动画之前先来看几个常用的 View 属性成员：
 * translationX，translationY：控制View的位置，值是相对于View容器左上角坐标的偏移。
 * rotationX，rotationY：控制相对于轴心旋转。
 * x，y：控制View在容器中的位置，即左上角坐标加上translationX和translationY的值。
 * alpha：控制View对象的alpha透明度值。
 * <p>
 * 属性动画的实现有7个类,平时使用属性动画的重点就在于 AnimatorSet、ObjectAnimator、TimeAnimator、ValueAnimator
 * ValueAnimator:<animator> 放置在 res/animator/ 目录下,在一个特定的时间里执行一个动画
 * TimeAnimator:不支持xml,时序监听回调工具
 * ObjectAnimator:<objectAnimator> 放置在 res/animator/ 目录下,一个对象的一个属性动画
 * AnimatorSet:<set> 放置在 res/animator/ 目录下,动画集合
 * <p>
 * Android 属性动画提供了以下属性：
 * Duration：动画的持续时间
 * TimeInterpolation：定义动画变化速率的接口，所有插值器都必须实现此接口，如线性、非线性插值器
 * TypeEvaluator：用于定义属性值计算方式的接口，有 int、float、color 类型，根据属性的起始、结束值和插值一起计算出当前时间的属性值
 * Animation sets：动画集合，即可以同时对一个对象应用多个动画，这些动画可以同时播放也可以对不同动画设置不同的延迟
 * Frame refreash delay：多少时间刷新一次，即每隔多少时间计算一次属性值，默认为 10ms，最终刷新时间还受系统进程调度与硬件的影响
 * Repeat Country and behavoir：重复次数与方式，如播放3次、5次、无限循环，可以让此动画一直重复，或播放完时向反向播放
 *
 */
public class AnimatorFragment extends BaseFragment {

    public static AnimatorFragment newInstance() {
        Bundle args = new Bundle();
        AnimatorFragment fragment = new AnimatorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_cartoon_animator;
    }

    @Override
    public void onClick(View v) {

    }
}
