package org.gmfbilu.superapp.module_view.stepView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

public class StepViewFragment extends BaseFragment {

    public static StepViewFragment newInstance() {
        Bundle args = new Bundle();
        StepViewFragment fragment = new StepViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
         return R.layout.module_view_fragment_stepview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
