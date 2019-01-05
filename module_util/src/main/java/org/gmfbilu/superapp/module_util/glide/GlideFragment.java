package org.gmfbilu.superapp.module_util.glide;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

public class GlideFragment extends BaseFragment {


    public static GlideFragment newInstance() {
        Bundle args = new Bundle();
        GlideFragment fragment = new GlideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_glide;
    }

    @Override
    public void onClick(View v) {
    }
}
