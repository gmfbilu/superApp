package org.gmfbilu.superapp.module_java.retrofit_rxjava;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.bean.request.FileReq;
import org.gmfbilu.superapp.lib_base.bean.request.GetDictionaryDatReq;
import org.gmfbilu.superapp.lib_base.bean.request.GetProductsByTypeReq;
import org.gmfbilu.superapp.lib_base.bean.request.LoginReq;
import org.gmfbilu.superapp.lib_base.bean.response.AddJJMergeRes;
import org.gmfbilu.superapp.lib_base.bean.response.FileRes;
import org.gmfbilu.superapp.lib_base.bean.response.LoginRes;
import org.gmfbilu.superapp.lib_base.http.FileUploadObserver;
import org.gmfbilu.superapp.lib_base.http.HttpMethods;
import org.gmfbilu.superapp.lib_base.http.HttpResult;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.util.PhotoUtils;
import org.gmfbilu.superapp.module_java.R;

import java.io.File;

public class RxJava_RetrofitFragment extends BaseFragment {

    private Toolbar mToolbar;

    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_ALBUM = 0x0011;//从相册选择照片

    public static RxJava_RetrofitFragment newInstance() {
        Bundle args = new Bundle();
        RxJava_RetrofitFragment fragment = new RxJava_RetrofitFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_java_toolbar);
        view.findViewById(R.id.module_java_bt_singleRequest).setOnClickListener(this);
        view.findViewById(R.id.module_java_bt_multiRequest).setOnClickListener(this);
        view.findViewById(R.id.module_java_bt_file).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_java_fragment_rxjava_retrofit;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_java_bt_singleRequest) {
            singleRequest();
        } else if (id == R.id.module_java_bt_multiRequest) {
            multiRequest();
        } else if (id == R.id.module_java_bt_file) {
            autoObtainGalleryCropPermission();
        }
    }


    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("RxJava&Retrofit");
        rxPermissions = new RxPermissions(_mActivity);
    }


    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }


    private void singleRequest() {
        LoginReq loginReq = new LoginReq();
        loginReq.login_name = "admin";
        loginReq.password = "123456";
        HttpMethods.getInstance().login(new NetObserver<LoginRes>() {

            @Override
            public void onNext(LoginRes loginRes) {
                super.onNext(loginRes);
                Toast.makeText(_mActivity, loginRes.toString(), Toast.LENGTH_SHORT).show();
            }
        }, loginReq, this);
    }

    private void multiRequest() {
        GetDictionaryDatReq getDictionaryDatReq = new GetDictionaryDatReq();
        GetProductsByTypeReq getProductsByTypeReq = new GetProductsByTypeReq();
        HttpMethods.getInstance().addJJMerge(new NetObserver<AddJJMergeRes>() {
            @Override
            public void onNext(AddJJMergeRes addJJMergeRes) {
                super.onNext(addJJMergeRes);
                Toast.makeText(_mActivity, addJJMergeRes.toString(), Toast.LENGTH_SHORT).show();
            }
        }, getDictionaryDatReq, getProductsByTypeReq, this);
    }

    private void uploadFile(File file) {
        FileReq fileReq = new FileReq();
        fileReq.file = file;
        HttpMethods.getInstance().uploadFile(new FileUploadObserver<HttpResult<FileRes>>() {
            @Override
            public void onUpLoadSuccess(HttpResult<FileRes> fileResHttpResult) {
                ToastUtil.show("上传成功");
            }

            @Override
            public void onUpLoadFail(Throwable e) {
                ToastUtil.show("上传失败"+e.getMessage());
                LoggerUtil.d("上传失败"+e.getMessage());
            }

            @Override
            public void onProgress(int progress) {
                LoggerUtil.d(progress);

            }
        }, fileReq, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_ALBUM) {
                if (data != null) {
                    //相册裁剪imageFile为null
                    Uri uri = data.getData();
                    LoggerUtil.d("uri:" + uri + "\nPath:" + uri.getPath() + "\n PhotoUtils.getPath:" + PhotoUtils.getPath(_mActivity, uri));//:uri:content://media/external/images/media/7088,//:Path:/external/images/media/7088
                    uploadFile(new File(PhotoUtils.getPath(_mActivity, uri)));
                }
            } else {
                LoggerUtil.e("错误码:" + resultCode);
            }
        }
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
}
