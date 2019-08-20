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
public class CameraFragment extends BaseFragment {

    //========================================================拍照
    private static final int IV_ALBUM = 0x100;
    private static final int IV_ALBUM_CROP = 0x101; //裁剪图片
    private static final int IV_CAMERA = 0x102;
    private static final int IV_CAMERA_CROP = 0x103; //裁剪图片
    private static final int IV_VIDEO = 0x104;
    //图片路径都在根路径下，方便用户删除
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    private String timeTemp = "/" + format.format(new Date());
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
     * 从相册选择照片后不裁剪后返回的有uri，视频也是。但是相册的uri需要转换，而视频不需要？
     * 直接拍照后没有uri,为什么
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
            //直接拍照不裁剪
            case IV_CAMERA:
                LoggerUtil.d(imageUri);
                LoggerUtil.d(imageUri.getPath());
                //imageUri在请求打开摄像头拍照的时候就已经指定了，如果成功了就直接使用这个uri
                Bitmap iv_camera_bitmap = PhotoUtils.getBitmapFromUri(imageUri, _mActivity);
                if (iv_camera_bitmap != null) {
                    iv_camera.setImageBitmap(iv_camera_bitmap);
                }
           /*     进行裁剪然后返回
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, IV_CAMERA_CROP);*/
                break;
            //从相册选取照片不裁剪
            //打开系统相册选择照片后，返回结果在Intent数据中的data字段，是一个uri，表示选择的那张照片的位置
            //打开系统相册选择照片的时候斌没有定uri
            case IV_ALBUM:
                if (AppUtils.hasSdcard()) {
                    Uri newUri = Uri.parse(PhotoUtils.getPath(_mActivity, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(_mActivity, AppUtils.getPackageName() + ".fileprovider", new File(newUri.getPath()));
                    }
                    Bitmap iv_album_bitmap = PhotoUtils.getBitmapFromUri(newUri, _mActivity);
                    if (iv_album_bitmap != null) {
                        LoggerUtil.d(newUri);// file:////storage/sdcard/DCIM/Camera/IMG_20190820_014108.jpg
                        LoggerUtil.d(newUri.getPath());//  //storage/sdcard/DCIM/Camera/IMG_20190820_014108.jpg
                        LoggerUtil.d(data.getData());// content://com.android.providers.media.documents/document/image%3A21
                        LoggerUtil.d(data.getData().getPath()); //  /document/image:21
                        iv_album.setImageBitmap(iv_album_bitmap);
                    }
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
            //录制视频后返回的结果在 Intent的data字段中，是一个uri，指向视频所保存的位置
            //uri在请求打开摄像头拍照的时候就已经指定了，如果成功了就直接使用这个uri
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
     * 打开系统相机
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
