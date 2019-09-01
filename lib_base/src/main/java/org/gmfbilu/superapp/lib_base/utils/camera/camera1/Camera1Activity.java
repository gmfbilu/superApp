package org.gmfbilu.superapp.lib_base.utils.camera.camera1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import org.gmfbilu.superapp.lib_base.R;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.util.BitmapUtils;
import org.gmfbilu.superapp.lib_base.utils.camera.util.FileUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.view.FaceView;

import java.io.File;
import java.util.ArrayList;

import okio.Okio;


public class Camera1Activity extends BaseActivity {

    private SurfaceView surfaceView;
    private FaceView faceView;
    private android.widget.ImageButton btnTakePic;
    private ImageView btnStart;
    private ImageView ivExchange;
    private ImageView btnStop;

    public static final String TYPE_TAG = "type";
    public static final int TYPE_CAPTURE = 0;
    public static final int TYPE_RECORD = 1;

    private boolean lock = false; //控制MediaRecorderHelper的初始化
    private Camera1Helper mCamera1Helper;
    private MediaRecorderHelper mMediaRecorderHelper;

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        surfaceView = findViewById(R.id.surfaceView);
        faceView = findViewById(R.id.faceView);
        btnTakePic = findViewById(R.id.btnTakePic);
        btnTakePic.setOnClickListener(this);
        btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        ivExchange = findViewById(R.id.ivExchange);
        ivExchange.setOnClickListener(this);
        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_lib_base_activity_camera1;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnTakePic) {
            mCamera1Helper.takePic();
        } else if (id == R.id.ivExchange) {
            mCamera1Helper.exchangeCamera();
        } else if (id == R.id.btnStart) {
            ivExchange.setClickable(false);
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);
            if (mMediaRecorderHelper != null) {
                mMediaRecorderHelper.startRecord();
            }
        } else if (id == R.id.btnStop) {
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);
            ivExchange.setClickable(true);
            if (mMediaRecorderHelper != null) {
                mMediaRecorderHelper.stopRecord();
            }
        }
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mCamera1Helper = new Camera1Helper(this, surfaceView);
        mCamera1Helper.addCallBack(new Camera1Helper.CallBack() {
            @Override
            public void onPreviewFrame(byte[] data) {
                if (!lock) {
                    Camera camera = mCamera1Helper.getCamera();
                    if (camera != null) {
                        new MediaRecorderHelper(Camera1Activity.this, camera, mCamera1Helper.mDisplayOrientation, mCamera1Helper.mSurfaceHolder.getSurface());
                        lock = true;
                    }
                }
            }

            @Override
            public void onTakePic(byte[] data) {
                savePic(data);
                btnTakePic.setClickable(true);
            }

            @Override
            public void onFaceDetect(ArrayList<RectF> faces) {
                faceView.setFaces(faces);
            }
        });

        if (getIntent().getIntExtra(TYPE_TAG, 0) == TYPE_RECORD) {//录视频
            btnTakePic.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    private void savePic(byte[] data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long temp = System.currentTimeMillis();
                    File picFile = FileUtil.createCameraFile("camera1");
                    if (picFile != null && data != null) {
                        Bitmap rawBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        Bitmap resultBitmap = (mCamera1Helper.mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) ? BitmapUtils.mirror(BitmapUtils.rotate(rawBitmap, 270f)) : BitmapUtils.rotate(rawBitmap, 90f);
                        if (mCamera1Helper.mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                            BitmapUtils.mirror(BitmapUtils.rotate(rawBitmap, 270f));
                        } else {
                            BitmapUtils.rotate(rawBitmap, 90f);
                        }
                        Okio.buffer(Okio.sink(picFile)).write(BitmapUtils.toByteArray(resultBitmap)).close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                LoggerUtil.d("图片已保存! 耗时：" + (System.currentTimeMillis() - temp) + "   路径：" + picFile.getAbsolutePath());
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show("保存图片失败！");
                        }
                    });
                }
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        mCamera1Helper.releaseCamera();
        if (mMediaRecorderHelper != null) {
            if (mMediaRecorderHelper.isRunning) {
                mMediaRecorderHelper.stopRecord();
            }
            mMediaRecorderHelper.release();
        }
        super.onDestroy();
    }

}
