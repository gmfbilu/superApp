package org.gmfbilu.superapp.module_util.glide;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

import androidx.appcompat.widget.AppCompatImageView;

public class GlideFragment extends BaseFragment {

    private AppCompatImageView iv;

    public static GlideFragment newInstance() {
        Bundle args = new Bundle();
        GlideFragment fragment = new GlideFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        iv = view.findViewById(R.id.iv);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_glide;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
