package org.gmfbilu.superapp.module_util.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_util.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraFragment extends BaseFragment {

    //========================================================拍照
    private static final int IV_ALBUM = 0x100;
    private static final int IV_ALBUM_CROP = 0x101; //裁剪图片
    private static final int IV_CAMERA = 0x102;
    private static final int IV_CAMERA_CROP = 0x103; //裁剪图片
    private static final int IV_VIDEO = 0x104;
    //图片路径都在根路径下，方便用户删除
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    String timeTemp = "/" + format.format(new Date());
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + timeTemp + ".jpg"); //图片质量是好几M以上
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + timeTemp + "crop.jpg");//图片质量可以压缩到几百K
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;


    private RxPermissions rxPermissions;
    private Toolbar mToolbar;
    private ImageView iv_album;
    private ImageView iv_camera;
    private ImageView iv_video;

    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        iv_album = view.findViewById(R.id.iv_album);
        iv_camera = view.findViewById(R.id.iv_camera);
        iv_video = view.findViewById(R.id.iv_video);
        view.findViewById(R.id.bt_album).setOnClickListener(this);
        view.findViewById(R.id.bt_camera).setOnClickListener(this);
        view.findViewById(R.id.bt_video).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_camera;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_album) {
            autoObtainGalleryPermission(IV_ALBUM);
        } else if (id == R.id.bt_camera) {
            autoObtainCameraPermission(IV_CAMERA);
        } else if (id == R.id.bt_video) {
            autoObtainVideoPermission(IV_VIDEO);
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
     * 图片返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK ) {
            return;
        }
        switch (requestCode) {
            case IV_CAMERA:
                LoggerUtil.d(imageUri);
                LoggerUtil.d(imageUri.getPath());
                Bitmap iv_camera_bitmap = PhotoUtils.getBitmapFromUri(imageUri, _mActivity);
                if (iv_camera_bitmap != null) {
                    iv_camera.setImageBitmap(iv_camera_bitmap);
                }
           /*     进行裁剪然后返回
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, IV_CAMERA_CROP);*/
                break;
                //从相册选取的照片为什么一定要这种方式
            case IV_ALBUM:
                cropImageUri = Uri.fromFile(fileCropUri);
                if (AppUtils.hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(_mActivity, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(_mActivity, AppUtils.getPackageName() + ".fileprovider", new File(newUri.getPath()));
                    }
                    //不进行裁剪
                    Bitmap iv_album_bitmap = PhotoUtils.getBitmapFromUri(newUri, _mActivity);
                    if (iv_album_bitmap != null) {
                        LoggerUtil.d(newUri);
                        LoggerUtil.d(newUri.getPath());
                        iv_album.setImageBitmap(iv_album_bitmap);
                    }
                    //PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    Toast.makeText(_mActivity, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
                }

                //进行裁剪然后返回
                //PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, IV_ALBUM_CROP);
                break;
            case IV_ALBUM_CROP:

                break;
            //裁剪返回
            case IV_CAMERA_CROP:
             /*   Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, _mActivity);
                if (bitmap != null) {
                    LoggerUtil.d(cropImageUri);
                    LoggerUtil.d(cropImageUri.getPath());
                    iv_camera.setImageBitmap(bitmap);
                }*/
                break;
            //拍摄视频返回
            case IV_VIDEO:
                Uri uri = data.getData();
                String videoPath = uri.getPath();
                LoggerUtil.d(uri); //file:///storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190820135637.mp4
                LoggerUtil.d(videoPath);///storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190820135637.mp4
                Bitmap videoBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
                Glide.with(_mActivity).load(videoBitmap).fitCenter().into(iv_video);
                //上传的时候video以file文件形式上传
                break;
            default:
        }
    }


    /**
     * 打开系统相机拍照
     */
    private void autoObtainCameraPermission(int who) {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            openSystemCamera(who);
                        } else {
                            Toast.makeText(_mActivity, "权限拒绝，请去设置中心打开相关权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    /**
     * 打开系统相册
     */
    private void autoObtainGalleryPermission(int who) {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            PhotoUtils.openPic(CameraFragment.this, who);
                        } else {
                            Toast.makeText(_mActivity, "请允许打操作SDCard！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 拍摄视频
     */
    private void autoObtainVideoPermission(int who) {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            takeVideo(who);
                        } else {
                            Toast.makeText(_mActivity, "权限拒绝，请去设置中心打开相关权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * 录像
     */
    private void takeVideo(int who) {
        // 激活系统的照相机进行录像
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");
        // 保存录像到指定的路径
        //获取与应用相关联的路径
        String videoPath = _mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String timeTemp = "/" + format.format(new Date()) + ".mp4";
        File file = new File(videoPath, timeTemp);
        Uri uri = Uri.fromFile(file);
        LoggerUtil.d(uri);//file:///storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190820135637.mp4
        LoggerUtil.d(uri.getPath());///storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190820135637.mp4
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, who);
    }


    /**
     * 打开系统相机并裁剪
     */
    private void openSystemCamera(int who) {
        if (AppUtils.hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            //通过FileProvider创建一个content类型的Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(_mActivity, AppUtils.getPackageName() + ".fileprovider", fileUri);
            }
            PhotoUtils.takePicture(this, imageUri, who);
        } else {
            Toast.makeText(_mActivity, "设备没有SD卡！", Toast.LENGTH_SHORT).show();
        }
    }

}
