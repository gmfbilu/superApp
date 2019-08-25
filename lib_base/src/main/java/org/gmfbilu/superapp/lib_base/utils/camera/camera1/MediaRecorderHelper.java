package org.gmfbilu.superapp.lib_base.utils.camera.camera1;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.Surface;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.camera.util.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class MediaRecorderHelper {

    @Nullable
    private MediaRecorder mMediaRecorder;
    public boolean isRunning;
    private String filePath;
    @NotNull
    private Activity mContext;
    private Camera mCamera;
    private int rotation;
    private Surface surface;


    public MediaRecorderHelper(@NotNull Activity mContext, Camera mCamera, int rotation, Surface surface) {
        this.mContext = mContext;
        this.mCamera = mCamera;
        this.rotation = rotation;
        this.surface = surface;
        filePath = FileUtil.createVideoFile().getAbsolutePath();
    }

    public void startRecord() {
        try {
            mMediaRecorder = new MediaRecorder();
            mCamera.unlock();//一定要调用
            mMediaRecorder.reset();
            mMediaRecorder.setCamera(mCamera);           //给Recorder设置Camera对象
            mMediaRecorder.setOrientationHint(rotation);  //改变保存后的视频文件播放时是否横屏(不加这句，视频文件播放的时候角度是反的)
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);    //设置从麦克风采集声音
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);  //设置从摄像头采集图像
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//设置视频的输出格式为MP4
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); //设置音频的编码格式
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);// 设置视频的编码格式
            mMediaRecorder.setVideoSize(3840, 2160);// 设置视频大小
            mMediaRecorder.setVideoFrameRate(60); // 设置帧率
            //it.setMaxDuration(10000) //设置最大录像时间为10s
            mMediaRecorder.setPreviewDisplay(surface); //设置
            mMediaRecorder.setOutputFile(filePath); //设置输出文件
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isRunning = true;
            LoggerUtil.d("开始录制视频");
            LoggerUtil.d("视频保存路径：" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
        }
        isRunning = false;
        LoggerUtil.d("停止录制视频");
        LoggerUtil.d("视频保存路径：" + filePath);
    }


    public void release() {
        if (mMediaRecorder != null) {
            mMediaRecorder.release();
            mMediaRecorder = null;
            isRunning = false;
        }
    }
}
