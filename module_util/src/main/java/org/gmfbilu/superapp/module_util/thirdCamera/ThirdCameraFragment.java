package org.gmfbilu.superapp.module_util.thirdCamera;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

public class ThirdCameraFragment extends BaseFragment {

    private AppCompatImageView iv;

    public static ThirdCameraFragment newInstance() {
        Bundle args = new Bundle();
        ThirdCameraFragment fragment = new ThirdCameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        iv = view.findViewById(R.id.module_util_iv_pic);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_third_camera;
    }

    @Override
    public void onClick(View v) {

    }
}
