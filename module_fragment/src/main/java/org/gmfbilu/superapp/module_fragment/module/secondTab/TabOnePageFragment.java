package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.lib_base.base.eventbusactivityscope.event.TabSelectedEvent;
import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.module.MainFragment;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class TabOnePageFragment extends BaseFragment implements View.OnClickListener {

    public static TabOnePageFragment newInstance() {
        Bundle args = new Bundle();
        TabOnePageFragment fragment = new TabOnePageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.fragment_tv_pageOne).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_tab_second_pager_one;
    }


    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.SECOND) return;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fragment_tv_pageOne) {
            // 通知MainFragment跳转至NewFeatureFragment,NewFeatureFragment是和MainFragment同级的
            //TabOnePageFragment的父Fragment是MainSecondFragment，而MainSecondFragment的父Fragment又是MainFragment
            ((MainFragment) getParentFragment().getParentFragment()).start(NewFeatureFragment.newInstance());
        }
    }
}
