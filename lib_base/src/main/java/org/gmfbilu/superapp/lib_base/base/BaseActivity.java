package org.gmfbilu.superapp.lib_base.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;

import org.gmfbilu.superapp.lib_base.app.ActivitiesManager;

import androidx.lifecycle.LifecycleOwner;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by gmfbilu on 2018/3/7.
 * <p>
 * 这种baseActivity不适合mvvm
 */

public abstract class BaseActivity extends SupportActivity implements View.OnClickListener, LifecycleOwner {


    /**
     * 判断当前Activity是否在前台。
     */
    protected Boolean isActivityActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getClass().getName() + "---> onCreate");
        int layout = setLayout();
        if (layout != 0)
            setContentView(layout);
        orientation();
        findViewById_setOnClickListener(savedInstanceState);
        ActivitiesManager.addActivity(this, getClass());
        //initPush();
    }

    public abstract void findViewById_setOnClickListener(Bundle savedInstanceState);

    public abstract int setLayout();


    private void initPush() {
        try {
            PushAgent.getInstance(this).onAppStart();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Logger.d(getClass().getName() + "---> onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Logger.d(getClass().getName() + "---> onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityActive = true;
        // Logger.d(getClass().getName() + "---> onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        isActivityActive = false;
        // Logger.d(getClass().getName() + "---> onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        // Logger.d(getClass().getName() + "---> onStop");
    }

    @Override
    protected void onDestroy() {
        ActivitiesManager.removeActivity(this);
        super.onDestroy();
        // Logger.d(getClass().getName() + "---> onDestroy");
    }

    /**
     * 配置全屏，一定要在setContentView之前调用
     */
    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 默认竖屏
     */
    protected void orientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


}
