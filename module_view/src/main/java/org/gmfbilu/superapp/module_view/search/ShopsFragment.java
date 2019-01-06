package org.gmfbilu.superapp.module_view.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

public class ShopsFragment extends BaseFragment {

    public static ShopsFragment newInstance() {
        Bundle args = new Bundle();
        ShopsFragment fragment = new ShopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_shops;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
