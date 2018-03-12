package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.eventbusactivityscope.EventBusActivityScope;
import org.gmfbilu.superapp.module_fragment.eventbusactivityscope.TabSelectedEvent;
import org.gmfbilu.superapp.module_fragment.module.MainFragment;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class TabOnePageFragment extends SupportFragment implements View.OnClickListener {

    public static TabOnePageFragment newInstance() {
        Bundle args = new Bundle();
        TabOnePageFragment fragment = new TabOnePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_second_pager_one, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        EventBusActivityScope.getDefault(_mActivity).register(this);
        view.findViewById(R.id.fragment_tv_pageOne).setOnClickListener(this);
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
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fragment_tv_pageOne) {
            ((MainFragment) getParentFragment().getParentFragment()).startBrotherFragment(NewFeatureFragment.newInstance());

        }
    }
}
