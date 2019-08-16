package org.gmfbilu.superapp.module_util.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_util.R;

import java.io.File;

public class CameraFragment extends BaseFragment {

    //========================================================拍照
    //打开相册请求码
    private static final int CODE_GALLERY_REQUEST = 100;
    //打开相机请求码
    private static final int CODE_CAMERA_REQUEST = 101;
    //裁剪图片返回码
    private static final int CODE_RESULT_REQUEST = 102;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;

    //=========================================================拍摄视频

    private RxPermissions rxPermissions;
    private Toolbar mToolbar;
    private ImageView mIV_pic;

    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        mIV_pic = view.findViewById(R.id.module_util_iv_pic);
        view.findViewById(R.id.module_util_bt_camera).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_album).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_video).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_camera;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_util_bt_camera) {
            autoObtainCameraPermission(true);
        } else if (id == R.id.module_util_bt_album) {
            autoObtainStoragePermission();
        } else if (id == R.id.module_util_bt_video) {
            autoObtainCameraPermission(false);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("Camera");
        rxPermissions = new RxPermissions(_mActivity);
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission(boolean photo) {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            if (photo) {
                                openSystemCamera();
                            } else {

                            }
                        } else {
                            Toast.makeText(_mActivity, "权限拒绝，请去设置中心打开相关权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            PhotoUtils.openPic(CameraFragment.this, CODE_GALLERY_REQUEST);
                        } else {
                            Toast.makeText(_mActivity, "请允许打操作SDCard！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * 图片返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                //进行裁剪然后返回
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                break;
            //相册返回
            case CODE_GALLERY_REQUEST:
                if (AppUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(_mActivity, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(_mActivity, AppUtils.getPackageName() + ".fileprovider", new File(newUri.getPath()));
                    }
                    //不进行裁剪
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(newUri, _mActivity);
                    if (bitmap != null) {
                        mIV_pic.setImageBitmap(bitmap);
                    }
                    //PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    Toast.makeText(_mActivity, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, _mActivity);
                if (bitmap != null) {
                    mIV_pic.setImageBitmap(bitmap);
                }
                break;
            default:
        }
    }

    /**
     * 打开系统相机并裁剪
     */
    private void openSystemCamera() {
        if (AppUtils.hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            //通过FileProvider创建一个content类型的Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(_mActivity, AppUtils.getPackageName() + ".fileprovider", fileUri);
            }
            PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
        } else {
            Toast.makeText(_mActivity, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
        }
    }

}
