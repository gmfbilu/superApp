package org.gmfbilu.superapp.lib_base.utils.camera.camera1;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.hardware.Camera;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraHelper implements Camera.PreviewCallback {

    //Camera对象
    private Camera mCamera;
    //Camera对象的参数
    private Camera.Parameters mParameters;
    //用于预览的SurfaceView对象
    private SurfaceView mSurfaceView;
    //SurfaceHolder对象
    public SurfaceHolder mSurfaceHolder;
    private Activity mActivity;
    //自定义的回调
    private CallBack mCallBack;
    //摄像头方向
    public int mCameraFacing = Camera.CameraInfo.CAMERA_FACING_BACK;
    //预览旋转的角度
    public int mDisplayOrientation = 0;
    //保存图片的宽
    private int picWidth = 2160;
    //保存图片的高
    private int picHeight = 3840;

    public CameraHelper(Activity activity, SurfaceView surfaceView) {
        this.mActivity = activity;
        this.mSurfaceView = surfaceView;
        mSurfaceHolder = mSurfaceView.getHolder();
        init();
    }


    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        mCallBack.onPreviewFrame(data);
    }

    public void takePic() {

    }

    private void init() {
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (mCamera == null) {
                    openCamera(mCameraFacing);
                }
                startPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                releaseCamera();
            }
        });
    }

    //打开相机, Camera.CameraInfo.CAMERA_FACING_BACK
    private boolean openCamera(int cameraFacing) {
        boolean supportCameraFacing = supportCameraFacing(cameraFacing);
        if (supportCameraFacing) {
            try {
                mCamera = Camera.open(cameraFacing);
                initParameters(mCamera);
                if (mCamera != null) {
                    mCamera.setPreviewCallback(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.show("打开相机失败!");
                return false;
            }
        }
        return supportCameraFacing;
    }

    //配置相机参数
    private void initParameters(Camera camera) {
        try {
            mParameters = camera.getParameters();
            mParameters.setPreviewFormat(ImageFormat.NV21);
            //获取与指定宽高相等或最接近的尺寸
            //设置预览尺寸
            Camera.Size bestPreviewSize = getBestSize(mSurfaceView.getWidth(), mSurfaceView.getHeight(), mParameters.getSupportedPreviewSizes());
            if (bestPreviewSize != null) {
                mParameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
            }
            //设置保存图片尺寸
            Camera.Size bestPicSize = getBestSize(picWidth, picHeight, mParameters.getSupportedPictureSizes());
            if (bestPicSize != null) {
                mParameters.setPictureSize(bestPicSize.width, bestPicSize.height);
            }
            //对焦模式
            if (isSupportFocus(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            }
            camera.setParameters(mParameters);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("相机初始化失败!");
        }
    }

    //开始预览
    private void startPreview() {
        try {
            if (mCamera != null) {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                setCameraDisplayOrientation(mActivity);
                mCamera.startPreview();
                startFaceDetect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            ToastUtil.show("相机预览失败!");
        }
    }

    private void startFaceDetect() {
        if (mCamera != null) {
            mCamera.startFaceDetection();
            mCamera.setFaceDetectionListener(new Camera.FaceDetectionListener() {
                @Override
                public void onFaceDetection(Camera.Face[] faces, Camera camera) {
                    if (mCallBack != null) {
                        mCallBack.onFaceDetect(transForm(faces));
                        LoggerUtil.d("检测到" + faces.length + "张人脸");
                    }
                }
            });
        }
    }

    //判断是否支持某一对焦模式
    private boolean isSupportFocus(String focusMode) {
        boolean autoFocus = false;
        List<String> listFocusMode = mParameters.getSupportedFocusModes();
        for (String mode : listFocusMode) {
            if (mode.equals(focusMode)) {
                autoFocus = true;
            }
            LoggerUtil.d("相机支持的对焦模式：" + mode);
        }
        return autoFocus;
    }

    //切换摄像头
    public void exchangeCamera() {
        releaseCamera();
        mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        openCamera(mCameraFacing);
        startPreview();
    }

    //释放相机
    public void releaseCamera() {
        if (mCamera != null) {
            // mCamera?.stopFaceDetection()
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    //获取与指定宽高相等或最接近的尺寸
    private Camera.Size getBestSize(int targetWidth, int targetHeight, List<Camera.Size> sizeList) {
        Camera.Size bestSize = null;
        double targetRatio = (targetHeight / targetWidth); //目标大小的宽高比
        double minDiff = targetRatio;
        for (int i = 0; i < sizeList.size(); i++) {
            Camera.Size size = sizeList.get(i);
            double supportedRatio = size.width / size.height;
            LoggerUtil.d("系统支持的尺寸 size.width * size.height:" + size.width * size.height + "  比例:" + supportedRatio);
        }
        for (Camera.Size size : sizeList) {
            if (size.width == targetHeight && size.height == targetWidth) {
                bestSize = size;
                break;
            }

            double supportedRatio = (size.width / size.height);
            if (Math.abs(supportedRatio - targetRatio) < minDiff) {
                minDiff = Math.abs(supportedRatio - targetRatio);
                bestSize = size;
            }
        }
        LoggerUtil.d("目标尺寸 ：" + targetWidth * targetHeight + "，   比例 :" + targetRatio);
        LoggerUtil.d("最优尺寸 ：" + bestSize.height * bestSize.width);
        return bestSize;
    }

    //设置预览旋转的角度
    private void setCameraDisplayOrientation(Activity activity) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraFacing, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int screenDegree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                screenDegree = 0;
                break;
            case Surface.ROTATION_90:
                screenDegree = 90;
                break;
            case Surface.ROTATION_180:
                screenDegree = 180;
                break;
            case Surface.ROTATION_270:
                screenDegree = 270;
                break;
        }
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mDisplayOrientation = (info.orientation + screenDegree) % 360;
            mDisplayOrientation = (360 - mDisplayOrientation) % 360;        // compensate the mirror
        } else {
            mDisplayOrientation = (info.orientation - screenDegree + 360) % 360;
        }
        mCamera.setDisplayOrientation(mDisplayOrientation);
        LoggerUtil.d("屏幕的旋转角度 :" + rotation);
        LoggerUtil.d("setDisplayOrientation(result) : " + mDisplayOrientation);
    }

    //判断是否支持某个相机
    private boolean supportCameraFacing(int cameraFacing) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraFacing) {
                return true;
            }
        }
        return false;
    }

    //将相机中用于表示人脸矩形的坐标转换成UI页面的坐标
    private ArrayList<RectF> transForm(Camera.Face[] faces) {
        Matrix matrix = new Matrix();
        // Need mirror for front camera.
        boolean mirror = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT);
        matrix.setScale(mirror ? -1f : 1f, 1f);
        // This is the value for android.hardware.Camera.setDisplayOrientation.
        matrix.postRotate(mDisplayOrientation);
        // Camera driver coordinates range from (-1000, -1000) to (1000, 1000).
        // UI coordinates range from (0, 0) to (width, height).
        matrix.postScale(mSurfaceView.getWidth() / 2000f, mSurfaceView.getHeight() / 2000f);
        matrix.postTranslate(mSurfaceView.getWidth() / 2f, mSurfaceView.getHeight() / 2f);

        ArrayList<RectF> rectList = new ArrayList<>();
        for (int i = 0; i < faces.length; i++) {
            Camera.Face face = faces[i];
            RectF srcRect = new RectF(face.rect);
            RectF dstRect = new RectF(0f, 0f, 0f, 0f);
            matrix.mapRect(dstRect, srcRect);
            rectList.add(dstRect);
        }
        return rectList;
    }

    public Camera getCamera() {
        return mCamera;
    }

    public void addCallBack(CallBack callBack) {
        this.mCallBack = callBack;
    }

    interface CallBack {
        void onPreviewFrame(byte[] data);

        void onTakePic(byte[] data);

        void onFaceDetect(ArrayList<RectF> faces);
    }
}
