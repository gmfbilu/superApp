package org.gmfbilu.superapp.lib_base.utils.camera.camera2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.util.BitmapUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import anet.channel.bytes.ByteArray;

public class Camera2Helper {

    //预览的宽度
    private final int PREVIEW_WIDTH = 720;
    //预览的高度
    private final int PREVIEW_HEIGHT = 1280;
    //保存图片的宽度
    private final int SAVE_WIDTH = 720;
    //保存图片的高度
    private final int SAVE_HEIGHT = 1280;

    private Activity mActivity;
    private TextureView mTextureView;

    private CameraManager mCameraManager;
    private ImageReader mImageReader;
    private CameraDevice mCameraDevice;
    private CameraCaptureSession mCameraCaptureSession;
    private String mCameraId = "0";
    private CameraCharacteristics mCameraCharacteristics;
    //摄像头方向
    private int mCameraSensorOrientation = 0;
    //默认使用后置摄像头
    private int mCameraFacing = CameraCharacteristics.LENS_FACING_BACK;
    //手机方向
    private int mDisplayRotation;
    //是否可以拍照
    private boolean canTakePic = true;
    //是否可以切换摄像头
    private boolean canExchangeCamera = false;
    private Handler mCameraHandler;
    private HandlerThread handlerThread = new HandlerThread("CameraThread");
    //预览大小
    private Size mPreviewSize = new Size(PREVIEW_WIDTH, PREVIEW_HEIGHT);
    //保存图片大小
    private Size mSavePicSize = new Size(SAVE_WIDTH, SAVE_HEIGHT);

    public Camera2Helper(Activity activity, TextureView textureView) {
        mActivity = activity;
        mTextureView = textureView;
        mDisplayRotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        init();
    }


    private void init() {
        handlerThread.start();
        mCameraHandler = new Handler(handlerThread.getLooper());
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                initCameraInfo();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                releaseCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
    }

    /**
     * 初始化
     */
    private void initCameraInfo() {
        mCameraManager = (CameraManager) mActivity.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] cameraIdList = mCameraManager.getCameraIdList();
            if (cameraIdList.length == 0) {
                ToastUtil.show("没有可用相机");
                LoggerUtil.d("没有可用相机");
                return;
            }
            for (int i = 0; i < cameraIdList.length; i++) {
                String id = cameraIdList[i];
                CameraCharacteristics cameraCharacteristics = mCameraManager.getCameraCharacteristics(id);
                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == null) {
                    break;
                }
                if (facing == mCameraFacing) {
                    mCameraId = id;
                    mCameraCharacteristics = cameraCharacteristics;
                }
                LoggerUtil.d("设备中的摄像头:" + id);
            }
            Integer supportLevel = mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            if (supportLevel == null) {
                LoggerUtil.d("supportLevel==null");
                return;
            }
            if (supportLevel == CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY) {
                LoggerUtil.d("相机硬件不支持新特性");
            }
            //获取摄像头方向
            mCameraSensorOrientation = mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            //获取StreamConfigurationMap，它是管理摄像头支持的所有输出格式和尺寸
            StreamConfigurationMap configurationMap = mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            //保存照片尺寸
            Size[] savePicSize = configurationMap.getOutputSizes(ImageFormat.JPEG);
            //预览尺寸
            Size[] previewSize = configurationMap.getOutputSizes(SurfaceTexture.class);
            boolean exchange = exchangeWidthAndHeight(mDisplayRotation, mCameraSensorOrientation);
            mSavePicSize = getBestSize(exchange ? mSavePicSize.getHeight() : mSavePicSize.getWidth(), exchange ? mSavePicSize.getWidth() : mSavePicSize.getHeight(), exchange ? mSavePicSize.getHeight() : mSavePicSize.getWidth(), exchange ? mSavePicSize.getWidth() : mSavePicSize.getHeight(), Arrays.asList(savePicSize));
            mPreviewSize = getBestSize(exchange ? mPreviewSize.getHeight() : mPreviewSize.getWidth(), exchange ? mPreviewSize.getWidth() : mPreviewSize.getHeight(), exchange ? mPreviewSize.getHeight() : mPreviewSize.getWidth(), exchange ? mPreviewSize.getWidth() : mPreviewSize.getHeight(), Arrays.asList(previewSize));
            mTextureView.getSurfaceTexture().setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
            LoggerUtil.d("预览最优尺寸 ：" + mPreviewSize.getWidth() * mPreviewSize.getHeight() + ", 比例" + mPreviewSize.getWidth() / mPreviewSize.getHeight());
            LoggerUtil.d("保存图片最优尺寸 ：${mSavePicSize.width} * ${mSavePicSize.height}, 比例  ${mSavePicSize.width.toFloat() / mSavePicSize.height}");
            LoggerUtil.d("保存图片最优尺寸 ：" + mSavePicSize.getWidth() * mSavePicSize.getHeight() + ", 比例" + mSavePicSize.getWidth() / mSavePicSize.getHeight());
            mImageReader = ImageReader.newInstance(mSavePicSize.getWidth(), mSavePicSize.getHeight(), ImageFormat.JPEG, 1);
            if (mImageReader != null) {
                mImageReader.setOnImageAvailableListener(onImageAvailableListener, mCameraHandler);
            }
            openCamera();
        } catch (CameraAccessException e) {
            e.printStackTrace();
            LoggerUtil.d(e.getMessage());
        }
    }


    /**
     * 打开相机
     */
    private void openCamera() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.show("没有相机权限！");
            return;
        }
        try {
            mCameraManager.openCamera(mCameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    LoggerUtil.d("onOpened");
                    mCameraDevice = camera;
                    createCaptureSession(camera);
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {
                    LoggerUtil.d("onDisconnected");
                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {
                    LoggerUtil.d("onError:" + error);
                    ToastUtil.show("打开相机失败" + error);
                }
            }, mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建预览会话
     */
    private void createCaptureSession(CameraDevice cameraDevice) {
        try {
            CaptureRequest.Builder captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            Surface surface = new Surface(mTextureView.getSurfaceTexture());
            // 将CaptureRequest的构建器与Surface对象绑定在一起
            captureRequestBuilder.addTarget(surface);
            // 闪光灯
            captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            // 自动对焦
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 为相机预览，创建一个CameraCaptureSession对象
            ArrayList<Surface> surfaces = new ArrayList<>();
            surfaces.add(surface);
            Surface mImageReaderSurface = mImageReader.getSurface();
            if (mImageReaderSurface != null) {
                surfaces.add(mImageReaderSurface);
            }
            cameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mCameraCaptureSession = session;
                    try {
                        session.setRepeatingRequest(captureRequestBuilder.build(), mCaptureCallBack, mCameraHandler);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    ToastUtil.show("开启预览会话失败");
                }
            }, mCameraHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    CameraCaptureSession.CaptureCallback mCaptureCallBack = new CameraCaptureSession.CaptureCallback() {
        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            super.onCaptureCompleted(session, request, result);
            canExchangeCamera = true;
            canTakePic = true;
        }

        @Override
        public void onCaptureFailed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureFailure failure) {
            super.onCaptureFailed(session, request, failure);
            LoggerUtil.d("onCaptureFailed");
            ToastUtil.show("开启预览失败！");
        }
    };

    /**
     * 根据提供的屏幕方向 [displayRotation] 和相机方向 [sensorOrientation] 返回是否需要交换宽高
     */
    private boolean exchangeWidthAndHeight(int displayRotation, int sensorOrientation) {
        boolean exchange = false;
        switch (displayRotation) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                if (sensorOrientation == 90 || sensorOrientation == 270) {
                    exchange = true;
                }
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                if (sensorOrientation == 0 || sensorOrientation == 180) {
                    exchange = true;
                }
                break;
            default:
                LoggerUtil.d("Display rotation is invalid:" + displayRotation);
                break;
        }
        LoggerUtil.d("屏幕方向:" + displayRotation);
        LoggerUtil.d("相机方向:" + sensorOrientation);
        return exchange;
    }

    /**
     * 根据提供的参数值返回与指定宽高相等或最接近的尺寸
     *
     * @param targetWidth  目标宽度
     * @param targetHeight 目标高度
     * @param maxWidth     最大宽度(即TextureView的宽度)
     * @param maxHeight    最大高度(即TextureView的高度)
     * @param sizeList     支持的Size列表
     * @return 返回与指定宽高相等或最接近的尺寸
     */
    private Size getBestSize(int targetWidth, int targetHeight, int maxWidth, int maxHeight, List<Size> sizeList) {
        ArrayList<Size> bigEnough = new ArrayList<>();    //比指定宽高大的Size列表
        ArrayList<Size> notBigEnough = new ArrayList<>();  //比指定宽高小的Size列表
        for (Size size : sizeList) {
            //宽<=最大宽度  &&  高<=最大高度  &&  宽高比 == 目标值宽高比
            if (size.getWidth() <= maxWidth && size.getHeight() <= maxHeight && size.getWidth() == size.getHeight() * targetWidth / targetHeight) {
                if (size.getWidth() >= targetWidth && size.getHeight() >= targetHeight) {
                    bigEnough.add(size);
                } else {
                    notBigEnough.add(size);
                }
            }
            LoggerUtil.d("系统支持的尺寸:" + size.getHeight() * size.getWidth() + ",  比例 ：" + size.getWidth() / size.getHeight());
        }
        LoggerUtil.d("最大尺寸 ：" + maxWidth * maxHeight + ", 比例 ：" + targetWidth / targetHeight);
        LoggerUtil.d("目标尺寸 ：" + targetWidth * targetHeight + ", 比例 ：" + targetWidth / targetHeight);
        //选择bigEnough中最小的值  或 notBigEnough中最大的值
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.min(notBigEnough, new CompareSizesByArea());
        } else {
            return sizeList.get(0);
        }
    }

    private class CompareSizesByArea implements Comparator<Size> {
        @Override
        public int compare(Size size1, Size size2) {
            return java.lang.Long.signum(size1.getWidth() * size1.getHeight() - size2.getWidth() * size2.getHeight());
        }
    }


    private ImageReader.OnImageAvailableListener onImageAvailableListener = new ImageReader.OnImageAvailableListener() {
        @Override
        public void onImageAvailable(ImageReader it) {
            Image image = it.acquireNextImage();
            ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
            ByteArray.create(byteBuffer.remaining());
            byte[] byteArray = new byte[byteBuffer.remaining()];
            byteBuffer.get(byteArray);
            it.close();
            BitmapUtils.savePic(byteArray, mCameraSensorOrientation == 270, new BitmapUtils.SavePicInterface() {
                @Override
                public void onSuccess(String savedPath, String time) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ToastUtil.show("图片保存成功！ 保存路径：" + savedPath + ", 耗时" + time);
                        }
                    });
                }

                @Override
                public void onFailed(String message) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show("图片保存失败!" + message);
                        }
                    });
                }
            });

        }
    };


    /**
     * 拍照
     */
    public void takePic() {
        if (mCameraDevice == null || !mTextureView.isAvailable() || !canTakePic) {
            return;
        }
        if (mCameraDevice != null) {
            try {
                CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                captureRequestBuilder.addTarget(mImageReader.getSurface());
                // 自动对焦
                captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                // 闪光灯
                captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                //根据摄像头方向对保存的照片进行旋转，使其为"自然方向"
                captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, mCameraSensorOrientation);
                mCameraCaptureSession.capture(captureRequestBuilder.build(),null,mCameraHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 切换摄像头
     */
    public void exchangeCamera() {
        if (mCameraDevice == null || !canExchangeCamera || !mTextureView.isAvailable()) {
            return;
        }
        mCameraFacing = (mCameraFacing == CameraCharacteristics.LENS_FACING_FRONT) ? CameraCharacteristics.LENS_FACING_BACK : CameraCharacteristics.LENS_FACING_FRONT;
        //重置预览大小
        mPreviewSize = new Size(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        releaseCamera();
        initCameraInfo();
    }


    public void releaseCamera() {
        if (mCameraCaptureSession != null) {
            mCameraCaptureSession.close();
            mCameraCaptureSession = null;
        }
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }
        if (mImageReader != null) {
            mImageReader.close();
            mImageReader = null;
        }
        canExchangeCamera = false;
    }

    public void releaseThread() {
        handlerThread.quitSafely();
    }


}
