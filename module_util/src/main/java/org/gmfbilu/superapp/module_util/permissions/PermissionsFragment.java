package org.gmfbilu.superapp.module_util.permissions;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.MessiObserver;
import org.gmfbilu.superapp.module_util.R;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PermissionsFragment extends BaseFragment {

    private RxPermissions rxPermissions;

    public static PermissionsFragment newInstance() {
        Bundle args = new Bundle();
        PermissionsFragment fragment = new PermissionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_util_bt_camera_permission).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_camera_read$external$storage_permission).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_permissions;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_util_bt_camera_permission) {
            requestCameraPermissions();
        } else if (id == R.id.module_util_bt_camera_read$external$storage_permission) {
            requestCameraAndExternal_storagePermissions();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxPermissions = new RxPermissions(_mActivity);
    }


    private void requestCameraPermissions() {
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new MessiObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) { // Always true pre-M
                                       // I can control the camera now
                                       openSystemCamera();
                                   } else {
                                       // Oups permission denied
                                       Toast.makeText(_mActivity, "Permission denied, can't enable the camera", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }


    private void requestCameraAndExternal_storagePermissions() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean granted) {
                        //All requested permissions are granted
                        if (granted) {
                            Toast.makeText(_mActivity, "all  is granted", Toast.LENGTH_SHORT).show();
                            // At least one permission is denied
                        } else {
                            Toast.makeText(_mActivity, "Permission denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void openSystemCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
}
