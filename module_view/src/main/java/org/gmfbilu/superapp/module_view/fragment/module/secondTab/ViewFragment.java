package org.gmfbilu.superapp.module_view.fragment.module.secondTab;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class ViewFragment extends BaseFragment {

    public static ViewFragment newInstance() {
        Bundle bundle = new Bundle();
        ViewFragment viewFragment = new ViewFragment();
        viewFragment.setArguments(bundle);
        return viewFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_new_feature_view;
    }

    @Override
    public void onClick(View v) {

    }
}
