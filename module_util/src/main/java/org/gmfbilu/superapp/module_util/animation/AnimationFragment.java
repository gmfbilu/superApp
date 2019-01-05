package org.gmfbilu.superapp.module_util.animation;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

public class AnimationFragment  extends BaseFragment {

    public static AnimationFragment newInstance() {
        Bundle args = new Bundle();
        AnimationFragment fragment = new AnimationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_animation;
    }

    @Override
    public void onClick(View v) {

    }
}
