package org.gmfbilu.superapp.module_util.animation;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

/**
 * Android 动画可以归纳为以下几种：
 *
 * 视图动画（View 动画）
 * 帧动画（Frame 动画、Drawable 动画）
 * 属性动画
 * 触摸反馈动画（Ripple Effect）
 * 揭露动画（Reveal Effect）
 * 转场动画 & 共享元素（Activity 切换动画）
 * 视图状态动画（Animate View State Changes）
 * 矢量图动画（Vector 动画）
 * 约束布局实现的关键帧动画（ConstraintSet 动画）
 */
public class CartoonFragment extends BaseFragment {

    public static CartoonFragment newInstance() {
        Bundle args = new Bundle();
        CartoonFragment fragment = new CartoonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_cartoon;
    }

    @Override
    public void onClick(View v) {

    }
}
