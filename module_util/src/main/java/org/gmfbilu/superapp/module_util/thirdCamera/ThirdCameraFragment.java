package org.gmfbilu.superapp.module_util.thirdCamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_util.R;

import java.util.List;

/**
 * 只是一个图片选择器，只是顺带了拍照功能。如果想直接进行拍照，打开相机即可
 */
public class ThirdCameraFragment extends BaseFragment {

    private RxPermissions rxPermissions;
    private static final int REQUEST_CODE_ALBUM = 101;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_VIDEO = 103;
    private static final int REQUEST_CODE_ALBUM_CAMERA = 104;

    private AppCompatImageView iv_album;
    private AppCompatImageView iv_camera;
    private AppCompatImageView iv_video;

    public static ThirdCameraFragment newInstance() {

        Bundle args = new Bundle();
        ThirdCameraFragment fragment = new ThirdCameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        iv_album = view.findViewById(R.id.iv_album);
        iv_camera = view.findViewById(R.id.iv_camera);
        iv_video = view.findViewById(R.id.iv_video);
        view.findViewById(R.id.bt_album).setOnClickListener(this);
        view.findViewById(R.id.bt_camera).setOnClickListener(this);
        view.findViewById(R.id.bt_video).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_third_camera;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        rxPermissions = new RxPermissions(_mActivity);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_album) {
            album();
        } else if (id == R.id.bt_camera) {
            //camera();
        } else if (id == R.id.bt_video) {
            video();
        }
    }


    private void album() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) {
                                       Matisse.from(ThirdCameraFragment.this)
                                               .choose(MimeType.ofVideo())
                                               .showSingleMediaType(true)//只出现照片，排除掉视频
                                               //.capture(true) 出现拍照选项
                                               //.captureStrategy(new CaptureStrategy(true, "org.gmfbilu.superapp.module_util.fileprovider", "test")) 有拍照选项的策略
                                               .theme(R.style.Matisse_Dracula)
                                               .countable(false)
                                               .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                               .maxSelectable(9)
                                               .originalEnable(true)
                                               .maxOriginalSize(10)
                                               .imageEngine(new Glide4Engine())
                                               .forResult(REQUEST_CODE_ALBUM);
                                   } else {
                                       Toast.makeText(_mActivity, "Permission denied, can't enable the camera", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }

    private void video() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) {
                                       Matisse.from(ThirdCameraFragment.this)
                                               .choose(MimeType.ofVideo())
                                               .showSingleMediaType(true)//只出现视频，排除掉照片
                                               .theme(R.style.Matisse_Dracula)
                                               .countable(false)
                                               .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                               .maxSelectable(9)
                                               .originalEnable(true)
                                               .maxOriginalSize(10)
                                               .imageEngine(new Glide4Engine())
                                               .forResult(REQUEST_CODE_ALBUM);
                                   } else {
                                       Toast.makeText(_mActivity, "Permission denied, can't enable the camera", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }

    private void albumWithCamera() {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new NetObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) {
                                       Matisse.from(ThirdCameraFragment.this)
                                               .choose(MimeType.ofAll(), false)
                                               .countable(true)
                                               .capture(true)
                                               .captureStrategy(new CaptureStrategy(true, "org.gmfbilu.superapp.module_util.fileprovider", "test"))
                                               .maxSelectable(9)
                                               .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                               .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                               .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                               .thumbnailScale(0.85f)
                                               .imageEngine(new Glide4Engine())
                                               .setOnSelectedListener(new OnSelectedListener() {
                                                   @Override
                                                   public void onSelected(
                                                           @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                                       // DO SOMETHING IMMEDIATELY HERE
                                                       Log.e("onSelected", "onSelected: pathList=" + pathList);

                                                   }
                                               })
                                               .originalEnable(true)
                                               .maxOriginalSize(10)
                                               .autoHideToolbarOnSingleTap(true)
                                               .setOnCheckedListener(new OnCheckedListener() {
                                                   @Override
                                                   public void onCheck(boolean isChecked) {
                                                       // DO SOMETHING IMMEDIATELY HERE
                                                       Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                                                   }
                                               })
                                               .forResult(REQUEST_CODE_ALBUM_CAMERA);
                                   } else {
                                       Toast.makeText(_mActivity, "Permission denied, can't enable the camera", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    List<Uri> uriList = Matisse.obtainResult(data);
                    for (int i = 0; i < uriList.size(); i++) {
                        Uri uri = uriList.get(i);
                        LoggerUtil.d("图片的Uri=" + uri);
                    }
                    List<String> pathResult = Matisse.obtainPathResult(data);
                    for (int i = 0; i < pathResult.size(); i++) {
                        LoggerUtil.d("图片的path=" + pathResult.get(i));
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                    break;
                case REQUEST_CODE_VIDEO:
                    List<Uri> uriListVideo = Matisse.obtainResult(data);
                    for (int i = 0; i < uriListVideo.size(); i++) {
                        Uri uri = uriListVideo.get(i);
                        LoggerUtil.d("视频的Uri=" + uri);
                    }
                    List<String> pathResultVideo = Matisse.obtainPathResult(data);
                    for (int i = 0; i < pathResultVideo.size(); i++) {
                        LoggerUtil.d("视频的path=" + pathResultVideo.get(i));
                    }
                    break;
                case REQUEST_CODE_ALBUM_CAMERA:
                    break;
            }
        }
    }


}
