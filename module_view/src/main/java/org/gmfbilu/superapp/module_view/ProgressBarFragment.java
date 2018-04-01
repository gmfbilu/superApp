package org.gmfbilu.superapp.module_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.views.CircleProgressBar;
import org.gmfbilu.superapp.module_view.views.HorizontalProgressBar;

/**
 * Created by gmfbilu on 2018/3/27.
 */

public class ProgressBarFragment extends BaseFragment {

    private Toolbar mToolbar;
    private CircleProgressBar mCircleProgressBar;
    private HorizontalProgressBar mHorizontalProgressBar;

    public static ProgressBarFragment newInstance() {
        Bundle args = new Bundle();
        ProgressBarFragment fragment = new ProgressBarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_view_toolbar);
        mCircleProgressBar = view.findViewById(R.id.module_view_circleProgressBar);
        mHorizontalProgressBar = view.findViewById(R.id.module_view_horizontalProgressBar);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_progressbar;
    }

    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("CircleProgressBar");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mCircleProgressBar.setProgressListener(new CircleProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {

            }
        });
        mCircleProgressBar.setProgressWithAnimation(60);
        mCircleProgressBar.startProgressAnimation();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onResume() {
        super.onResume();
        mCircleProgressBar.resumeProgressAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCircleProgressBar.pauseProgressAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCircleProgressBar.stopProgressAnimation();
    }
}
