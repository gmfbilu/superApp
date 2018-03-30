package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_fragment.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class TabThreePageFragment extends BaseFragment {

    public static TabThreePageFragment newInstance() {
        Bundle args = new Bundle();
        TabThreePageFragment fragment = new TabThreePageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_tab_second_pager_three;
    }

    @Override
    public void onClick(View v) {

    }
}
