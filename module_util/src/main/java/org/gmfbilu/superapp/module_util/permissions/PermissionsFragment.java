package org.gmfbilu.superapp.module_util.permissions;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
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
        view.findViewById(R.id.module_util_bt_camera_read_external_storage_permission).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_call).setOnClickListener(this);
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
        } else if (id == R.id.module_util_bt_camera_read_external_storage_permission) {
            requestCameraAndExternal_storagePermissions();
        } else if (id == R.id.module_util_bt_call) {
            requestCallPermissions();
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
                .subscribe(new NetObserver<Boolean>() {
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


    private void requestCallPermissions() {
        rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(new NetObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) { // Always true pre-M
                                       Intent intent = new Intent();//Intent.ACTION_CALL,Uri.parse(listphone.get(position))
                                       intent.setAction(Intent.ACTION_CALL);//直接拨出电话
                                       //intent.setAction(Intent.ACTION_DIAL);//只调用拨号界面，不拨出电话
                                       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                       intent.setData(Uri.parse("tel:" + "10086"));
                                       startActivity(intent);
                                   } else {
                                       // Oups permission denied
                                       Toast.makeText(_mActivity, "Permission denied", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }
}
