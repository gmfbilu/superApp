package org.gmfbilu.superapp.module_fragment.module.firstTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.eventbusactivityscope.EventBusActivityScope;
import org.gmfbilu.superapp.lib_base.base.eventbusactivityscope.event.TabSelectedEvent;
import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.base.BaseMainFragment;
import org.gmfbilu.superapp.module_fragment.module.MainFragment;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by gmfbilu on 18-3-11.
 * 主界面第一个Tab的Fragment
 */

public class MainFirstFragment extends BaseMainFragment {

    public static MainFirstFragment newInstance() {
        Bundle args = new Bundle();
        MainFirstFragment fragment = new MainFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        //订阅
        EventBusActivityScope.getDefault(_mActivity).register(this);
        view.findViewById(R.id.module_fragment_tv_go).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_fragment_fragment_tab_first;
    }

    /**
     * 懒加载,同级Fragment场景、ViewPager场景均适用
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.module_fragment_tv_go) {
            // 因为启动的OneLayerFragment是MainFragment的兄弟Fragment,所以需要MainFragment.start()。但是MainFirstFragment又是MainFragment上层Fragment
            // 通知MainFragment跳转至OneLayerFragment
            ((MainFragment) getParentFragment()).start(OneLayerFragment.newInstance(new Message("hello")));

        }
    }

    /**
     * 接收EventBus消息
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;

 /*       if (mInAtTop) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            scrollToTop();
        }*/

    }


    /**
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消订阅
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}
