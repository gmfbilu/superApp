package org.gmfbilu.superapp.module_util.recyclerView.multiType;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.recyclerView.headerFooter.HeaderFooterRecyclerViewFragment;

public class MultiTypeRecyclerViewFragment extends BaseFragment {

    public static MultiTypeRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        MultiTypeRecyclerViewFragment fragment = new MultiTypeRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
