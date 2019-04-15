package org.gmfbilu.superapp.module_util.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_util.R;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * BluetoothAdapter表示本地蓝牙适配器，是所有蓝牙交互的入口点。利用它可以发现其他蓝牙设备，
 * 查询配对设备的列表，使用已知的MAC地址实例化BluetoothDevice，以及创建BluetoothServerSocket以监听来自其它设备的通信
 * <p>
 * BluetoothDevice表示远程蓝牙设备，利用它可以通过BluetoothSocket请求与某个远程设备建立连接，或查询有关该设备的信息，例如设备的名称、地址、类和绑定状态等
 * <p>
 * BluetoothSocket表示蓝牙套接字接口，这是允许应用通过InputStream和OutputStream与其它蓝牙设备交换数据的连接点
 * <p>
 * BluetoothServerSocket表示用于侦查听传入请求的开发服务器套接字，要连接两台Android设备，其中一台设备必须使用此类开放一个服务器套接字，
 * 当一台远程蓝牙设备向此设备发出连接请求时，BluetoothServerSocket将会在接受连接后返回以连接的BluetoothSocket
 * <p>
 * BluetoothClass描述蓝牙设备的一般特征和功能
 * <p>
 * BluetoothProfile表示藍牙配置文件的接口，藍牙配置文件是适用于设备间蓝牙通信的无线接口规范
 * <p>
 * BluetoothA2dp定义高质量音频如何通过蓝牙连接和流式传输，从一台设备传输到另一台设备。A2DP代表高级音频分发配置文件
 * <p>
 * BluetoothHeadset提供蓝牙耳机支持，以便与手机配合使用
 * <p>
 * BluetoothHealth表示用于控制蓝牙服务的健康设备配置文件代理
 * <p>
 * BluetoothHealthCallback用于实现BluetoothHealth回调的抽象类，必须扩展此类并实现回调方法，以接收关于应用注册状态和蓝牙通道状态变化的更新内容
 * <p>
 * BluetoothHealthAppConfiguration表示第三方蓝牙健康应用注册的应用配置，以便与远程蓝牙健康设备通信
 * <p>
 * BluetoothProfile.ServiceListener在BluetoothProfile 或断开服务连接是向其发送通知的接口
 * <p>
 * 使用蓝牙功能，必须声明BLUETOOTH权限。操作蓝牙设备，必须声明BLUETOOTH_ADMIN权限。这两个权限是普通权限
 * <p>
 * 监听蓝牙广播必须添加权限ACCESS_COARSE_LOCATION,ACCESS_FINE_LOCATION
 */

public class BlueToothFragment extends BaseFragment {

    private BluetoothAdapter mBluetoothAdapter;
    public final static int REQUEST_ENABLE_BT = 999;
    private ArrayList<String> deviceList = new ArrayList<>();

    public static BlueToothFragment newInstance() {
        Bundle args = new Bundle();
        BlueToothFragment fragment = new BlueToothFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_check_support_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.bt_open_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.bt_close_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.bt_find_matched_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.bt_find_bluetooth).setOnClickListener(this);
        view.findViewById(R.id.bt_attached_other).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_bluetooth;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_check_support_bluetooth) {
            if (mBluetoothAdapter != null) {
                AppUtils.toast("支持");
            } else {
                AppUtils.toast("不支持");
            }
        } else if (id == R.id.bt_open_bluetooth) {
            if (mBluetoothAdapter.isEnabled()) {
                AppUtils.toast("蓝牙已经开启");
                return;
            }
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        } else if (id == R.id.bt_close_bluetooth) {
            if (!mBluetoothAdapter.isEnabled()) {
                AppUtils.toast("蓝牙已经关闭");
                return;
            }
            mBluetoothAdapter.disable();
        } else if (id == R.id.bt_find_matched_bluetooth) {
            if (!mBluetoothAdapter.isEnabled()) {
                AppUtils.toast("请先打开蓝牙");
                return;
            }
            //之前已经配对成功过的蓝牙
            Set<BluetoothDevice> bondedDevices = mBluetoothAdapter.getBondedDevices();
            if (bondedDevices.size() > 0) {
                for (BluetoothDevice device : bondedDevices) {
                    AppUtils.toast(device.getName() + ", " + bondedDevices.size());
                }
            }
        } else if (id == R.id.bt_find_bluetooth) {
            if (!mBluetoothAdapter.isEnabled()) {
                AppUtils.toast("请先打开蓝牙");
                return;
            }
            //开启发现设备，该方法是异步方法，发现进程通常包含约12秒的查询扫描，之后对每台发现的设备进行页面扫描，以检索蓝牙名称
            //发现设备对于蓝牙来说是一个非常繁重的过程，并且会消耗大量资源，找到设备后使用cancelDiscovery停止发现
            Single.timer(12, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Long>() {

                        //不显示指定线程的时候onSubscribe在主线程，onSuccess在子线程
                        @Override
                        public void onSubscribe(Disposable d) {
                            mBluetoothAdapter.startDiscovery();
                            Logger.d("onSubscribe");
                            AppUtils.toast("开始查找蓝牙设备");
                        }

                        @Override
                        public void onSuccess(Long aLong) {
                            mBluetoothAdapter.cancelDiscovery();
                            Logger.d("onSuccess");
                            AppUtils.toast(deviceList.size() + "个蓝牙设备");
                        }

                        @Override
                        public void onError(Throwable e) {
                            mBluetoothAdapter.cancelDiscovery();
                            Logger.d("onError");
                        }
                    });
        } else if (id == R.id.bt_attached_other) {
            if (!mBluetoothAdapter.isEnabled()) {
                AppUtils.toast("请先打开蓝牙");
                return;
            }
            //默认情况下设备变为可检测并持续120s，可设置最大持续时间为3600s，任何小于0或者大于3600s的值都会被自动设为120s
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                AppUtils.toast("蓝牙已经开启");
            } else if (resultCode == RESULT_CANCELED) {
                AppUtils.toast("蓝牙没有开启");
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        _mActivity.registerReceiver(mBroadcastReceiver, makeFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        _mActivity.unregisterReceiver(mBroadcastReceiver);
    }


    private IntentFilter makeFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        return intentFilter;
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                Logger.d("BluetoothAdapter.ACTION_STATE_CHANGED");
                int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                switch (blueState) {
                    case BluetoothAdapter.STATE_ON:
                        AppUtils.toast("蓝牙开启");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        AppUtils.toast("蓝牙正在开启");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        AppUtils.toast("蓝牙正在关闭");
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        AppUtils.toast("蓝牙关闭");
                        break;
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // TODO: 3/27/19 获取不到数据 
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Logger.d("BluetoothDevice.ACTION_FOUND");
                deviceList.add(device.getName());
            }
        }
    };
}
