package org.gmfbilu.superapp.lib_base.utils.camera.camera2;

import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

import org.gmfbilu.superapp.lib_base.R;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.utils.camera.view.FaceView;

public class Camera2Activity extends BaseActivity {

    private Camera2Helper mCamera2Helper;
    private TextureView textureView;
    private FaceView faceView;

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        textureView = findViewById(R.id.textureView);
        faceView = findViewById(R.id.faceView);
        findViewById(R.id.btnTakePic).setOnClickListener(this);
        findViewById(R.id.ivExchange).setOnClickListener(this);
        mCamera2Helper = new Camera2Helper(this, textureView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_lib_base_activity_camera2;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnTakePic) {
            mCamera2Helper.takePic();
        } else if (id == R.id.ivExchange) {
            mCamera2Helper.exchangeCamera();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera2Helper.releaseCamera();
        mCamera2Helper.releaseThread();
    }
}
