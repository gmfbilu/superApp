package org.gmfbilu.superapp;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.orhanobut.logger.Logger;
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
