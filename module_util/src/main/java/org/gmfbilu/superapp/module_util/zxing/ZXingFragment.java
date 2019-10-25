package org.gmfbilu.superapp.module_util.zxing;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.http.NetObserver;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.utils.ZXingUtils;
import org.gmfbilu.superapp.lib_base.utils.zxing.android.CaptureActivity;
import org.gmfbilu.superapp.module_util.R;

public class ZXingFragment extends BaseFragment {

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private RxPermissions rxPermissions;

    private ImageView iv_generate;

    public static ZXingFragment newInstance() {
        Bundle args = new Bundle();
        ZXingFragment fragment = new ZXingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_scan).setOnClickListener(this);
        view.findViewById(R.id.bt_generate).setOnClickListener(this);
        iv_generate = view.findViewById(R.id.iv_generate);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_zxing;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.bt_scan) {
            requestCameraPermissions();
        } else if (id == R.id.bt_generate) {
            Bitmap qrImage = ZXingUtils.createQRImage("世界和平", 300, 300);
            LoggerUtil.d(iv_generate.getWidth());
            iv_generate.setImageBitmap(qrImage);
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        rxPermissions = new RxPermissions(_mActivity);
    }

    private void requestCameraPermissions() {
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(new NetObserver<Boolean>() {
                               @Override
                               public void onNext(Boolean granted) {
                                   if (granted) { // Always true pre-M
                                       goScan();
                                   } else {
                                       // Oups permission denied
                                       Toast.makeText(_mActivity, "Permission denied, can't enable the camera", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           }
                );
    }

    /**
     * 跳转到扫码界面扫码
     */
    private void goScan() {
        Intent intent = new Intent(_mActivity, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                //返回的文本内容
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //返回的BitMap图像
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                ToastUtil.show(content);
            }
        }
    }
}
