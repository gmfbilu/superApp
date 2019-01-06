package org.gmfbilu.superapp.module_view.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

public class LocalFragment extends BaseFragment {

    public static LocalFragment newInstance() {
        Bundle args = new Bundle();
        LocalFragment fragment = new LocalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_local;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }
}
