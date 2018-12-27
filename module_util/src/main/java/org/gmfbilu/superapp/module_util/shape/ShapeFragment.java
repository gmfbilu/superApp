package org.gmfbilu.superapp.module_util.shape;

import android.os.Bundle;
import android.view.View;
import org.gmfbilu.superapp.module_util.R;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class ShapeFragment extends BaseFragment {

    public static ShapeFragment newInstance() {
        Bundle args = new Bundle();
        ShapeFragment fragment = new ShapeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_shape;
    }

    @Override
    public void onClick(View v) {

    }
}
