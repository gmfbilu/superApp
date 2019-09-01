package org.gmfbilu.superapp.module_util.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.camera.camera1.Camera1Activity;
import org.gmfbilu.superapp.module_util.R;

/**
 *
 * https://github.com/smashinggit/Study
 * 进行Camra开发主要用到了以下两个类:
 * 1.Camera
 * 2.SurfaceView (当然也可以是TextureView)
 * SurfaceView继承自View，其中有两个成员变量，一个是Surface对象，一个是SuraceHolder对象
 * SurfaceView把Surface显示在屏幕上
 * SurfaceView通过SuraceHolder告诉我们Surface的状态（创建、变化、销毁）
 *
 * Camera类中的内部类
 * CameraInfo
 * CameraInfo类用来描述相机信息，通过Camera类中getCameraInfo(int cameraId, CameraInfo cameraInfo)方法获得，主要包括以下两个成员变量：
 * facing 代表相机的方向，它的值只能是CAMERA_FACING_BACK（后置摄像头） 或者CAMERA_FACING_FRONT（前置摄像头）
 * CAMERA_FACING_BACK 和 CAMERA_FACING_FRONT 是CameraInfo类中的静态变量
 * orientation是相机采集图片的角度。这个值是相机所采集的图片需要顺时针旋转至自然方向的角度值。它必须是0，90,180或270中的一个
 * 相机图像数据都是来自于相机硬件的图像传感器（Image Sensor），这个Sensor被固定到手机之后是有一个默认的取景方向，且不会改变
 * 相机在预览的时候是有一个预览方向的，可以通过setDisplayOrientation()设置
 * 相机所采集的照片也是有一个方向的(就是上面刚刚提到的orientation)，这个方向与预览时的方向互不相干
 * 屏幕坐标： 在Android系统中，屏幕的左上角是坐标系统的原点（0,0）坐标。原点向右延伸是X轴正方向，原点向下延伸是Y轴正方向
 * 自然方向：每个设备都有一个自然方向，手机和平板的自然方向不同。手机的自然方向是portrait（竖屏），平板的自然方向是landscape（横屏）
 * 图像传感器（Image Sensor）方向：手机相机的图像数据都是来自于摄像头硬件的图像传感器，这个传感器在被固定到手机上后有一个默认的取景方向
 * 相机的预览方向：将图像传感器捕获的图像，显示在屏幕上的方向。在默认情况下，与图像传感器方向一致。在相机API中可以通过setDisplayOrientation()设置相机预览方向。在默认情况下，这个值为0，与图像传感器方向一致
 * 绝大部分安卓手机中图像传感器方向是横向的，且不能改变，所以orientation是90或是270，也就是说，当点击拍照后保存图片的时候，需要对图片做旋转处理，使其为"自然方向"。 （可能存在一些特殊的定制或是能外接摄像头的安卓机，他们的orientation会是0或者180）
 * 通过setDisplayOrientation方法设置预览方向，使预览画面为"自然方向"。前置摄像头在进行角度旋转之前，图像会进行一个水平的镜像翻转，所以用户在看预览图像的时候就像照镜子一样
 * 图片大小，里面包含两个变量：width和height（图片的宽和高）
 * Parameters是相机服务设置，不同的相机可能是不相同的。比如相机所支持的图片大小，对焦模式等等
 * getSupportedPreviewSizes()获得相机支持的预览图片大小，返回值是一个List<Size>数组
 * setPreviewSize(int width, int height)设置相机预览图片的大小
 * getSupportedPreviewFormats()获得相机支持的图片预览格式，所有的相机都支持ImageFormat.NV21,更多的图片格式可以自行百度或是查看ImageFormat类
 * setPreviewFormat(int pixel_format)设置预览图片的格式
 * getSupportedPictureSizes()获得相机支持的采集的图片大小(即拍照后保存的图片的大小)
 * setPictureSize(int width, int height)设置保存的图片的大小
 * getSupportedPictureFormats()获得相机支持的图片格式
 * setPictureFormat(int pixel_format)设置保存的图片的格式
 * getSupportedFocusModes()获得相机支持的对焦模式
 * setFocusMode(String value)设置相机的对焦模式
 * getMaxNumDetectedFaces()返回当前相机所支持的最大的人脸检测个数
 * PreviewCallback是一个抽象接口,void onPreviewFrame(byte[] data, Camera camera)通过onPreviewFrame方法来获取到相机预览的数据，第一个参数data，就是相机预览到的原始数据.这些预览到的原始数据是非常有用的，比如我们可以保存下来当做一张照片，还有很多第三方的人脸检测及静默活体检测的sdk，都需要我们把相机预览的数据实时地传递过去。
 * Face类用来描述通过Camera的人脸检测功能检测到的人脸信息
 * rect 是一个Rect对象，它所表示的就是检测到的人脸的区域
 * score检测到的人脸的可信度，范围是1 到100
 * leftEye,leftEye 是一个Point对象，表示检测到的左眼的位置坐标
 * mouth,mouth是一个Point对象，表示检测到的嘴的位置坐标
 * leftEye ，rightEye和mouth这3个人脸中关键点，并不是所有相机都支持的，如果相机不支持的话，这3个的值为null
 * FaceDetectionListener,这是一个抽象接口，当开始人脸检测时开始回调,onFaceDetection(Face[] faces, Camera camera)一参数代表检测到的人脸，是一个Face数组(画面内可能存在多张人脸)
 *
 * Camera类中的方法
 * getNumberOfCameras()返回当前设备上可用的摄像头个数
 * open()使用当前设备上第一个后置摄像头创建一个Camera对象。如果当前设备没有后置摄像头，则返回值为null
 * open(int cameraId)使用传入id所表示的摄像头创建一个Camera对象，如果id所表示的摄像头已经被打开，则会抛出异常,设备上每一个物理摄像都是有一个id的，id从0开始，到getNumberOfCameras() - 1 结束
 * 例如，一般的手机上都有前后两个摄像头，那么后置摄像头id就是0，前置摄像头id就是1
 * getCameraInfo(int cameraId, CameraInfo cameraInfo)返回指定id所表示的摄像头的信息
 *
 * Camera负责采集数据和各种操作，SurfaceView负责把Camera采集到的数据实时地显示在屏幕上
 * 在关闭页面 或者 打开其他摄像头之前，一定要先调用release()方法释放当前相机资源
 *
 */
public class CameraFragment extends BaseFragment {


    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.btCamera).setOnClickListener(this);
        view.findViewById(R.id.btCameraRecord).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_camera;
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btCamera) {
            Intent intent = new Intent(_mActivity, Camera1Activity.class);
            intent.putExtra(Camera1Activity.TYPE_TAG, Camera1Activity.TYPE_CAPTURE);
            startActivity(intent);
        } else if (id == R.id.btCameraRecord) {
            Intent intent = new Intent(_mActivity, Camera1Activity.class);
            intent.putExtra(Camera1Activity.TYPE_TAG, Camera1Activity.TYPE_RECORD);
            startActivity(intent);
        }
    }


}
