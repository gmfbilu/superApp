package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.aidl.AidlFragment;
import org.gmfbilu.superapp.module_util.cartoon.CartoonFragment;
import org.gmfbilu.superapp.module_util.aop.AOPFragment;
import org.gmfbilu.superapp.module_util.camera.CameraFragment;
import org.gmfbilu.superapp.module_util.glide.GlideFragment;
import org.gmfbilu.superapp.module_util.jni.JniFragment;
import org.gmfbilu.superapp.module_util.permissions.PermissionsFragment;
import org.gmfbilu.superapp.module_util.thirdCamera.ThirdCameraFragment;


public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_util_bt_permissions).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_camera).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_thirdCamera).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_jni).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_aidl).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_aop).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_glide).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_animation).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_util_bt_permissions) {
            start(PermissionsFragment.newInstance());
        } else if (id == R.id.module_util_bt_camera) {
            start(CameraFragment.newInstance());
        } else if (id == R.id.module_util_bt_thirdCamera) {
            start(ThirdCameraFragment.newInstance());
        } else if (id == R.id.module_util_bt_jni) {
            start(JniFragment.newInstance());
        } else if (id == R.id.module_util_bt_aidl) {
            start(AidlFragment.newInstance());
        } else if (id == R.id.module_util_bt_aop) {
            start(AOPFragment.newInstance());
        } else if (id == R.id.module_util_bt_glide) {
            start(GlideFragment.newInstance());
        } else if (id == R.id.module_util_bt_animation) {
            start(CartoonFragment.newInstance());
        }
    }
}
