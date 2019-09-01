package org.gmfbilu.superapp.module_util.capture;

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


/**
 * 在7.0以上系统中，Android不再允许在app中把file://Uri暴露给其他app
 * 在Android6.0之后引入运行时权限，如果接收file://Uri的app没有申请READ_EXTERNAL_STORAGE权限，在读取文件时会引发崩溃
 * 官方给出的解决方案是使用FileProvider
 * <provider
 * android:name="android.support.v4.content.FileProvider" //name:provider的类名,若使用默认的v4的FileProvide
 * android:authorities="com.cs.camerademo.fileProvider"//android:authorities 一个签名认证，可以自定义，但在获取uri的时候需要保持一致
 * android:exported="false"
 * android:grantUriPermissions="true">//grantUriPermissions:使用FileProvider的使用需要我们给流出的URI赋予临时访问权限（READ和WRITE），该设置是允许我们行使该项权力
 * <meta-data //meta-data: 配置的是我们可以访问的文件的路径配置信息，需要使用xml文件进行配置，FileProvider会通过解析xml文件获取配置项
 * android:name="android.support.FILE_PROVIDER_PATHS" //其中name为固定写法：android.support.FILE_PROVIDER_PATHS
 * android:resource="@xml/file_paths" /> //resource为配置路径信息的配置项目
 * </provider>
 * <p>
 * <?xml version="1.0" encoding="utf-8"?>
 * <paths> //这个xml文件最外层是一对<paths>标签，标签内部是一个或多个表示路径的item.，写法是 <对应的path name="起个名字" path="对应的path目录下的某个文件夹" >
 * //external-path 表示 Environment.getExternalStorageDirectory()目录，name 是我们给它起的名字叫"external-path"，path 表示Environment.getExternalStorageDirectory()目录下的CameraDemo文件夹
 * <external-path name="external-path" path="CameraDemo" />
 * </paths>
 * //<files-path name="name" path="path" >  Context.getFilesDir()
 * //<cache-path name="name" path="path">  Context.getCacheDir()
 * //<external-path name="name" path="path">  Environment.getExternalStorageDirectory()
 * //<external-files-path name="name" path="path">  Context.getExternalFilesDir(null)
 * //<external-cache-path name="name" path="path">  Context.getExternalCacheDir()
 * //如果需要使用FileProvider获取某个目录下的文件的uri，按照上表中的对应关系在xml文件中声明就可以了.在清单文件中注册FileProvider的时候， android:resource="@xml/file_paths" 对应的就是我们刚刚创建的这个xml文件
 * <p>
 * Uri.parse需要有协议，如file://等，用Uri.parse(url)，url就要加"file://"开头
 * Uri.parse("file://" + new File(path).toString()) 和 Uri.fromFile(new File(path))是等价的
 */

public class CaptureFragment extends BaseFragment {

    private static final String AUTHORITY = "org.gmfbilu.superapp.fileprovider";
    private static final int REQUEST_CODE_CROP = 0x0010;//裁剪
    private static final int REQUEST_CODE_ALBUM = 0x0011;//从相册选择照片
    private static final int REQUEST_CODE_CAPTURE = 0x0012;//拍照
    private static final int REQUEST_CODE_CAPTURE_SMALL = 0x0013;//拍照缩略图
    private static final int REQUEST_CODE_CAPTURE_RAW = 0x0014;//拍照原始图
    private static final int REQUEST_CODE_VIDEO = 0x0015;//录视频

    private Uri imgUri;//剪裁原图的Uri
    private File imageFile;//剪裁原图的File
    private File imageCropFile;//剪裁后的图片的File
    private File videoFile;

    private RxPermissions rxPermissions;
    private Toolbar mToolbar;
    private ImageView iv_getPicture;
    private android.widget.VideoView vv_video;

    public static CaptureFragment newInstance() {
        Bundle args = new Bundle();
        CaptureFragment fragment = new CaptureFragment();
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
        return R.layout.module_util_fragment_capture;
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
                    LoggerUtil.d(imageCropFile);
                    LoggerUtil.d(imageCropFile.getAbsolutePath());
                    iv_getPicture.setImageBitmap(BitmapFactory.decodeFile(imageCropFile.getAbsolutePath()));
                }
            } else if (requestCode == REQUEST_CODE_ALBUM) {
                if (data != null) {
                    //相册裁剪imageFile为null
                    gotoCrop(data.getData());
                }
            } else if (requestCode == REQUEST_CODE_CAPTURE) {
                //拍照后裁剪imageFile不为null
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
                    String path = uri.getPath();
                    vv_video.setVideoURI(uri);
                    vv_video.start();
                    LoggerUtil.d("视频uri:" + uri + "\n" + "视频path:" + path);
                    //:file:///storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190827094205.mp4
                    //:/storage/emulated/0/Android/data/org.gmfbilu.superapp.module_util/files/Pictures/20190827094205.mp4
                    LoggerUtil.d(videoFile.getAbsolutePath());//: /storage/emulated/0/CameraDemo/video/VIDEO_20190827_100324.mp4
                    //上传视频使用videoFile.getAbsolutePath()，上传之前要压缩视频

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
     * 打开系统相册
     */
    private void autoObtainGalleryCropPermission() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
     * 拍照
     */
    private void autoObtainCaptureCropPermission() {
        rxPermissions
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                                LoggerUtil.d("imageFile:" + imageFile + "\n imgUri:" + imgUri);
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
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                .request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                .request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            videoFile = FileUtil.createVideoFile();//:/storage/emulated/0/CameraDemo/video/VIDEO_20190827_094205.mp4
                            LoggerUtil.d(videoFile.getAbsolutePath());
                            if (videoFile != null) {
                                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    Uri videoUri = FileProvider.getUriForFile(_mActivity, AUTHORITY, videoFile);//: content://org.gmfbilu.superapp.fileProvider/external-path/capture/IMG_20190826_000350.jpg
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                                } else {
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
                                }
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 60);//限制时间
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 10 * 1024 * 1024L);//限制录制大小(10M=10 * 1024 * 1024L)
                                ComponentName componentName = intent.resolveActivity(_mActivity.getPackageManager());
                                if (componentName != null) {
                                    startActivityForResult(intent, REQUEST_CODE_VIDEO);
                                }
                            }
                        } else {
                            ToastUtil.show("没有录取音视频权限，请去设置中心打开相关权限");
                        }
                    }
                });
    }

    /**
     * 裁剪
     */
    private void gotoCrop(Uri sourceUri) {
        imageCropFile = FileUtil.createImageFile(true);
        LoggerUtil.d("sourceUri:" + sourceUri + "\n imageCropFile:" + imageCropFile);//:/storage/emulated/0/CameraDemo/capture/IMG_20190826_164409_CROP.jpg
        if (imageCropFile != null) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //需要加上这两句话 ： uri 权限
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(sourceUri, "image/*");  //设置数据源
                Uri imgCropUri = Uri.fromFile(imageCropFile);
                LoggerUtil.d(imgCropUri);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri); //设置输出  不需要ContentUri,否则失败
            } else {
                if (imageFile != null) {
                    //拍照后imageFile已经赋值，但是相册imageFile为null
                    LoggerUtil.d(imageFile);
                    LoggerUtil.d(Uri.fromFile(imageFile));
                    intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
                } else {
                    intent.setDataAndType(sourceUri, "image/*");
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageCropFile));
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);  //X方向上的比例
            intent.putExtra("aspectY", 1);  //Y方向上的比例
            intent.putExtra("outputX", 500);  //裁剪区的宽
            intent.putExtra("outputY", 500); //裁剪区的高
            intent.putExtra("scale ", true); //是否保留比例
            intent.putExtra("return-data", false);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);//取消人脸识别功能
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
