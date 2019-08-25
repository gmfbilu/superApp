package org.gmfbilu.superapp.module_util.camera;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.util.DecodeImgTask;
import org.gmfbilu.superapp.lib_base.utils.camera.util.FileUtil;
import org.gmfbilu.superapp.module_util.R;

import java.io.File;

public class CameraTestFragment extends BaseFragment {

    private static final String AUTHORITY = "org.gmfbilu.superapp.fileprovider";
    private static final int REQUEST_CODE_CROP = 0x0010;//裁剪
    private static final int REQUEST_CODE_ALBUM = 0x0011;//从相册选择照片
    private static final int REQUEST_CODE_CAPTURE = 0x0012;//拍照
    private static final int REQUEST_CODE_CAPTURE_SMALL = 0x0013;//拍照缩略图
    private static final int REQUEST_CODE_CAPTURE_RAW = 0x0014;//拍照原始图
    private static final int REQUEST_CODE_VIDEO = 0x0015;//录视频

    private Uri imgUri;
    private File imageFile;
    private File imageCropFile;

    private RxPermissions rxPermissions;
    private Toolbar mToolbar;
    private ImageView iv_getPicture;
    private android.widget.VideoView vv_video;

    public static CameraTestFragment newInstance() {
        Bundle args = new Bundle();
        CameraTestFragment fragment = new CameraTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        iv_getPicture = view.findViewById(R.id.iv_getPicture);
        vv_video = view.findViewById(R.id.vv_video);
        view.findViewById(R.id.bt_getPicture).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_camera;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("Camera");
        rxPermissions = new RxPermissions(_mActivity);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_getPicture) {
            showDialog();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CROP) {
                if (imageCropFile != null) {
                    iv_getPicture.setImageBitmap(BitmapFactory.decodeFile(imageCropFile.getAbsolutePath()));
                }
            } else if (requestCode == REQUEST_CODE_ALBUM) {
                if (data != null) {
                    gotoCrop(data.getData());
                }
            } else if (requestCode == REQUEST_CODE_CAPTURE) {
                Uri sourceUri = FileProvider.getUriForFile(_mActivity, AUTHORITY, imageFile); //通过FileProvider创建一个content类型的Uri
                gotoCrop(sourceUri);
            } else if (requestCode == REQUEST_CODE_CAPTURE_SMALL) {
                if (data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    iv_getPicture.setImageBitmap(bitmap);
                }
            } else if (requestCode == REQUEST_CODE_CAPTURE_RAW) {
                if (imageFile != null) {
                    new DecodeImgTask(iv_getPicture).execute(imageFile.getAbsolutePath());
                }
            } else if (requestCode == REQUEST_CODE_VIDEO) {
                if (data != null) {
                    Uri uri = data.getData();
                    vv_video.setVideoURI(uri);
                    vv_video.start();
                    LoggerUtil.d("视频uri:" + uri);
                }
            } else {
                LoggerUtil.e("错误码:" + resultCode);
            }
        }
    }


    private void showDialog() {
        String[] item = {"相册+裁剪", "拍照+裁剪", "拍照(返回原图)", "拍照(返回缩略图)", "录视频"};
        AlertDialog.Builder builder = new AlertDialog.Builder(_mActivity);
        builder.setTitle("请选择");
        builder.setItems(item, (dialog, which) -> {
            if (which == 0) {
                autoObtainGalleryCropPermission();
            } else if (which == 1) {
                autoObtainCaptureCropPermission();
            } else if (which == 2) {
                autoObtainCaptureRowPermission();
            } else if (which == 3) {
                autoObtainCaptureSmallPermission();
            } else if (which == 4) {
                autoObtainVideoPermission();
            }
        });
        // 取消可以不添加
        //builder.setNegativeButton("取消",null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * 打开系统相册并裁剪
     */
    private void autoObtainGalleryCropPermission() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, REQUEST_CODE_ALBUM);
                        } else {
                            ToastUtil.show("没有读取照片权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    /**
     * 拍照并裁剪
     */
    private void autoObtainCaptureCropPermission() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            imageFile = FileUtil.createImageFile(false);//: /storage/emulated/0/CameraDemo/capture/IMG_20190826_000350.jpg
                            if (imageFile != null) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    imgUri = FileProvider.getUriForFile(_mActivity, AUTHORITY, imageFile);//: content://org.gmfbilu.superapp.fileProvider/external-path/capture/IMG_20190826_000350.jpg
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                } else {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                                }
                                LoggerUtil.d("imageFile:"+imageFile+"\n imgUri:"+imgUri);
                                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                                ComponentName componentName = intent.resolveActivity(_mActivity.getPackageManager());
                                if (componentName != null) {
                                    startActivityForResult(intent, REQUEST_CODE_CAPTURE);
                                }
                            }
                        } else {
                            ToastUtil.show("没有拍照、读取照片权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    /**
     * 拍照(返回原始图)
     */
    private void autoObtainCaptureRowPermission() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            imageFile = FileUtil.createImageFile(false);
                            if (imageFile != null) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    imgUri = FileProvider.getUriForFile(_mActivity, AUTHORITY, imageFile);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                } else {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                                }
                                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                                ComponentName componentName = intent.resolveActivity(_mActivity.getPackageManager());
                                if (componentName != null) {
                                    startActivityForResult(intent, REQUEST_CODE_CAPTURE_RAW);
                                }
                            }
                        } else {
                            ToastUtil.show("没有拍照、读取照片权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    /**
     * 拍照(返回缩略图)
     */
    private void autoObtainCaptureSmallPermission() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            ComponentName componentName = intent.resolveActivity(_mActivity.getPackageManager());
                            if (componentName != null) {
                                startActivityForResult(intent, REQUEST_CODE_CAPTURE_SMALL);
                            }
                        } else {
                            ToastUtil.show("没有拍照、读取照片权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    /**
     * 录视频
     */
    private void autoObtainVideoPermission() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            ComponentName componentName = intent.resolveActivity(_mActivity.getPackageManager());
                            if (componentName != null) {
                                startActivityForResult(intent, REQUEST_CODE_VIDEO);
                            }
                        } else {
                            ToastUtil.show("没有录取音视频权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    //裁剪
    private void gotoCrop(Uri sourceUri) {
        imageCropFile = FileUtil.createImageFile(true);
        if (imageCropFile != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);  //X方向上的比例
            intent.putExtra("aspectY", 1);  //Y方向上的比例
            intent.putExtra("outputX", 500);  //裁剪区的宽
            intent.putExtra("outputY", 500); //裁剪区的高
            intent.putExtra("scale ", true); //是否保留比例
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setDataAndType(sourceUri, "image/*");  //设置数据源
                Uri imgCropUri = Uri.fromFile(imageCropFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri); //设置输出  不需要ContentUri,否则失败
            } else {
                intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            }
            LoggerUtil.d("输入:" + sourceUri + "\n 输出:" + Uri.fromFile(imageCropFile));
            startActivityForResult(intent, REQUEST_CODE_CROP);
        }
    }


    @Override
    public void onDestroy() {
        if (vv_video.isPlaying()) {
            vv_video.pause();
        }
        super.onDestroy();
    }
}
