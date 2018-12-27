package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.aidl.AidlFragment;
import org.gmfbilu.superapp.module_util.aop.AOPFragment;
import org.gmfbilu.superapp.module_util.camera.CameraFragment;
import org.gmfbilu.superapp.module_util.dynamicLayout.DynamicLayoutFragment;
import org.gmfbilu.superapp.module_util.jni.JniFragment;
import org.gmfbilu.superapp.module_util.permissions.PermissionsFragment;
import org.gmfbilu.superapp.module_util.shape.ShapeFragment;
import org.gmfbilu.superapp.module_util.surfaceView.SurfaceViewFragment;
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
        view.findViewById(R.id.module_util_bt_surfaceView).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_jni).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_aidl).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_dynamicLayout).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_shape).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_aop).setOnClickListener(this);
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
        } else if (id == R.id.module_util_bt_surfaceView) {
            start(SurfaceViewFragment.newInstance());
        } else if (id == R.id.module_util_bt_jni) {
            start(JniFragment.newInstance());
        } else if (id == R.id.module_util_bt_aidl) {
            start(AidlFragment.newInstance());
        } else if (id == R.id.module_util_bt_dynamicLayout) {
            start(DynamicLayoutFragment.newInstance());
        } else if (id == R.id.module_util_bt_shape) {
            start(ShapeFragment.newInstance());
        } else if (id == R.id.module_util_bt_aop) {
            start(AOPFragment.newInstance());
        }
    }
}
