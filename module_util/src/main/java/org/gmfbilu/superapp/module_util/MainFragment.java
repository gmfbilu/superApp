package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;


public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_util_bt_camera).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.module_util_bt_camera){
            start(CameraFragment.newInstance());
        }
    }
}
