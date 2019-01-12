package org.gmfbilu.superapp.module_view.dynamicLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.R;

/**
 * 1. xml中引入的根布局通过getLayoutParams获取的LayoutParams默认是FrameLayout.LayoutParams.
 * 如果在getLayoutParams方法调用之前new了一个LayoutParams并且调用setLayoutParams方法关联,
 * 那么通过getLayoutParams方法得到的LayoutParams就是new出来类型的LayoutParams，
 * 并且调用setLayoutParams方法进行关联之后，之后再改变LayoutParams的参数都无需调用setLayoutParams方法进行再次关联
 * 2. new创建的根布局通过getLayoutParams获取的LayoutParams默认null,可以通过new任意类型的LayoutParams得到一个LayoutParams，
 * 再通过setLayoutParams方法进行关联,之后再通过getLayoutParams方法得到的LayoutParams就是new出来类型的LayoutParams，
 * 并且调用setLayoutParams方法进行关联之后，之后再改变LayoutParams的参数都无需调用setLayoutParams方法进行再次关联
 * <p>
 * 1.通过new出来的控件被addView方法添加到父布局中去之后，然后new控件的LayoutParams,这时候LayoutParams的类型只能是父布局类型的LayoutParams,
 * 此时通过getLayoutParams方法得到的LayoutParams类型也一定是父布局类型的LayoutParams
 * 2.通过new出来的控件还没有被addView方法添加到父布局中去之前，new控件的LayoutParams,这时候LayoutParams的类型可以是任意类型的LayoutParams,
 * 此时通过getLayoutParams方法得到的LayoutParams对象为null
 * <p>
 * 1.new 控件
 * 2.配置ID
 * 3.将控件通过addView方法添加到父控件中去
 * 4.控件通过getLayoutParams方法得到的父控件类型的LayoutParams，然后配置宽高等参数
 * 5.控件通过setLayoutParams方法关联LayoutParams
 */
public class DynamicLayoutFragment extends BaseFragment {

    private ConstraintLayout content;

    public static DynamicLayoutFragment newInstance() {
        Bundle args = new Bundle();
        DynamicLayoutFragment fragment = new DynamicLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        content = new ConstraintLayout(_mActivity);
        initView();
        return content;
    }

    private void initView() {
        helloDynamicLayout();
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
    }

    private void helloDynamicLayout() {
        //获取父布局的LayoutParams改变父布局的参数.注:content为根布局，
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0, 100, 0, 0);
        content.setLayoutParams(layoutParams);
        //给View配置ID,id在values下的ids文件中
        content.setId(R.id.module_view_dynamicLayout_content);


        AppBarLayout appBarLayout = new AppBarLayout(_mActivity);
        appBarLayout.setId(R.id.module_view_dynamicLayout_appBarLayout);
        content.addView(appBarLayout);
        //约束布局既可以使用LayoutParams配置参数又可以使用ConstraintSet配置参数，ConstraintSet更为强大，可以配置动画
        ConstraintLayout.LayoutParams ConstraintLayout_LayoutParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ConstraintLayout_LayoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        appBarLayout.setLayoutParams(ConstraintLayout_LayoutParams);


        Toolbar toolbar = new Toolbar(_mActivity);
        toolbar.setId(R.id.module_view_toolbar);
        appBarLayout.addView(toolbar);
        AppBarLayout.LayoutParams AppBarLayout_LayoutParams = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.setLayoutParams(AppBarLayout_LayoutParams);
        toolbar.setPopupTheme(android.support.v7.appcompat.R.style.ThemeOverlay_AppCompat_Dark_ActionBar);
        toolbar.setOnClickListener(this);
        toolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        toolbar.setTitle("DynamicLayout");
        toolbar.setTitleTextColor(Color.WHITE);

        TextView tv_guideline = new TextView(_mActivity);
        tv_guideline.setId(R.id.module_view_tv_guideline);
        content.addView(tv_guideline);
        //使用ConstraintSet
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.constrainHeight(tv_guideline.getId(), ConstraintLayout.LayoutParams.WRAP_CONTENT);
        constraintSet.constrainWidth(tv_guideline.getId(), ConstraintLayout.LayoutParams.WRAP_CONTENT);
        constraintSet.connect(tv_guideline.getId(), ConstraintSet.TOP, appBarLayout.getId(), ConstraintSet.BOTTOM, 100);
        constraintSet.applyTo(content);
        tv_guideline.setText("Guideline!");
        tv_guideline.setBackgroundColor(Color.parseColor("#FF4081"));

        TextView tv_baseline = new TextView(_mActivity);
        tv_baseline.setId(R.id.module_view_tv_baseline);
        content.addView(tv_baseline);
        ConstraintLayout.LayoutParams layoutParams_tv_baseline = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_tv_baseline.topToBottom = tv_guideline.getId();
        tv_baseline.setLayoutParams(layoutParams_tv_baseline);
        tv_baseline.setText("Baseline!");
        tv_baseline.setBackgroundColor(Color.parseColor("#3F51B5"));

        TextView tv_bias = new TextView(_mActivity);
        tv_bias.setId(R.id.module_view_tv_bias);
        content.addView(tv_bias);
        ConstraintLayout.LayoutParams layoutParams_tv_bias = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams_tv_bias.topToBottom = tv_baseline.getId();
        layoutParams_tv_bias.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams_tv_bias.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams_tv_bias.horizontalBias = 0.3f;
        tv_bias.setLayoutParams(layoutParams_tv_bias);
        tv_bias.setText("Bias!");
        tv_bias.setBackgroundColor(Color.parseColor("#3F51B5"));

        TextView tv_dimenRadio = new TextView(_mActivity);
        tv_dimenRadio.setId(R.id.module_view_tv_dimenRadio);
        content.addView(tv_dimenRadio);
        ConstraintLayout.LayoutParams layoutParams_tv_dimenRadio = new ConstraintLayout.LayoutParams(AppUtils.dp2px(120), 0);
        layoutParams_tv_dimenRadio.topToBottom = tv_bias.getId();
        layoutParams_tv_dimenRadio.dimensionRatio = "3:2";
        tv_dimenRadio.setLayoutParams(layoutParams_tv_dimenRadio);
        tv_dimenRadio.setText("dimenRadio!");
        tv_dimenRadio.setBackgroundColor(Color.parseColor("#3F51B5"));

    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     *          content为xml文件中的约束布局
     *          View view = new View(this);
     *         view.setBackgroundColor(Color.WHITE);
     *         view.setId(view.generateViewId());
     *         content.addView(view);
     *         ConstraintLayout.LayoutParams view_layoutParams = new ConstraintLayout.LayoutParams(0, AppUtils.dip2px(this, 44f));
     *         view_layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
     *         view_layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
     *         view_layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
     *         view.setLayoutParams(view_layoutParams);
     *         ConstraintLayout.LayoutParams recyclerview_layoutParams = new ConstraintLayout.LayoutParams(0, 0);
     *         recyclerview_layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
     *         recyclerview_layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
     *         View childAt = content.getChildAt(0);
     *         childAt.setId(childAt.generateViewId());
     *         recyclerview_layoutParams.topToBottom = childAt.getId();
     *         recyclerview_layoutParams.bottomToTop = view.getId();
     *         mBaseRecyclerView.setLayoutParams(recyclerview_layoutParams);
     */
}
