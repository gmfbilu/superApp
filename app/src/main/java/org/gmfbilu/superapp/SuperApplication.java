package org.gmfbilu.superapp;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
import com.taobao.sophix.SophixManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;

/**
 * Created by gmfbilu on 2018/3/2.
 */

public class SuperApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 并不是所有的第三方库都可以放在IntentService中
         */
       // initPush();
        /**
         * 该方法主要用于查询服务器是否有新的可用补丁. SDK内部限制连续两次queryAndLoadNewPatch()方法调用不能短于3s, 否则的话就会报code:19的错误码. 如果查询到可用的话, 首先下载补丁到本地, 然后
         * 应用原本没有补丁, 那么如果当前应用的补丁是热补丁, 那么会立刻加载(不管是冷补丁还是热补丁). 如果当前应用的补丁是冷补丁, 那么需要重启生效.
         * 应用已经存在一个补丁, 请求发现有新补丁后，本次不受影响。并且在下次启动时补丁文件删除, 下载并预加载新补丁。在下下次启动时应用新补丁
         * 补丁在后台发布之后, 并不会主动下行推送到客户端, 需要手动调用queryAndLoadNewPatch方法查询后台补丁是否可用
         * 只会下载补丁版本号比当前应用存在的补丁版本号高的补丁, 比如当前应用已经下载了补丁版本号为5的补丁, 那么只有后台发布的补丁版本号>5才会重新下载
         */
        SophixManager.getInstance().queryAndLoadNewPatch();
        Log.e("step","SuperApplication");
    }

    private void initPush() {
        UMConfigure.init(this, Constant.PUSH_APPKEY, AppUtils.getChannelName(), UMConfigure.DEVICE_TYPE_PHONE, Constant.PUSH_SECRET);
        UMConfigure.setLogEnabled(Constant.ISSHOWLOG);
        PushAgent mPushAgent = PushAgent.getInstance(this);
        /**
         * 注册推送服务，每次调用register方法都会回调该接口
         */
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.d(Constant.LOG_NAME, "推送服务注册成功，deviceToken：" + deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.d(Constant.LOG_NAME, "推送服务注册失败，错误信息：" + s + ", " + s1);
            }
        });
        /**
         * 自定义通知打开动作
         * 自定义行为的数据放在UMessage.custom字段
         */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {

            @Override
            public void dealWithCustomAction(Context context, UMessage uMessage) {
                super.dealWithCustomAction(context, uMessage);
                Logger.d(uMessage.custom);
                switch (uMessage.custom) {
                    case "module_main_googleLibrary":
                        //打开Activity
                        ARouter.getInstance()
                                .build(ARouterPath.MODULE_GOOGLELIBRARY)
                                //.withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                                .navigation();

                        break;

                }
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

    }
}
