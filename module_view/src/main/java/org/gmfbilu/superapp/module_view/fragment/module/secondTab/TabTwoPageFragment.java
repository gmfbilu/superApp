package org.gmfbilu.superapp.module_view.fragment.module.secondTab;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class TabTwoPageFragment extends BaseFragment {

    public static TabTwoPageFragment newInstance() {
        Bundle args = new Bundle();
        TabTwoPageFragment fragment = new TabTwoPageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_tab_second_pager_two;
    }

    @Override
    public void onClick(View v) {

    }
}
