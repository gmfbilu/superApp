package org.gmfbilu.superapp.module_view.surfaceView;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


public class SurfaceViewFragment extends BaseFragment {

    private Toolbar mToolbar;

    public static SurfaceViewFragment newInstance() {
        Bundle args = new Bundle();
        SurfaceViewFragment fragment = new SurfaceViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_surfaceview;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

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
        mToolbar.setTitle("SurfaceView");
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

}
