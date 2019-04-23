package org.gmfbilu.superapp.module_view.topbar;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

/**
 * 各种头部布局
 */
public class TopBarFragment extends BaseFragment {

    public static TopBarFragment newInstance() {
        Bundle args = new Bundle();
        TopBarFragment fragment = new TopBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_topbar;
    }

    @Override
    public void onClick(View v) {

    }
}
