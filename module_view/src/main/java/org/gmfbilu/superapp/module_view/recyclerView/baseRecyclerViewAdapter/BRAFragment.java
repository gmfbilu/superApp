package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.simple.BRASimpleFragment;

public class BRAFragment extends BaseFragment {

    public static BRAFragment newInstance() {
        Bundle args = new Bundle();
        BRAFragment fragment = new BRAFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_simple).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_base_recyclerview_adapter;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_simple) {
            start(BRASimpleFragment.newInstance());
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
