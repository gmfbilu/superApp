package org.gmfbilu.superapp.module_fragment.module.firstTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.base.BaseMainFragment;
import org.gmfbilu.superapp.module_fragment.eventbusactivityscope.EventBusActivityScope;
import org.gmfbilu.superapp.module_fragment.eventbusactivityscope.TabSelectedEvent;
import org.gmfbilu.superapp.module_fragment.module.MainFragment;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by gmfbilu on 18-3-11.
 * 主界面第一个Tab的Fragment
 */

public class MainFirstFragment extends BaseMainFragment implements View.OnClickListener {

    private TextView fragment_tv_go;

    public static MainFirstFragment newInstance() {
        Bundle args = new Bundle();
        MainFirstFragment fragment = new MainFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_first, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        fragment_tv_go = view.findViewById(R.id.fragment_tv_go);
        fragment_tv_go.setOnClickListener(this);
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }


    /**
     * 主要代码
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
        if (i == R.id.fragment_tv_go) {
            ((MainFragment) getParentFragment()).startBrotherFragment(OneLayerFragment.newInstance(new Message("hello")));

        }
    }

    /**
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
