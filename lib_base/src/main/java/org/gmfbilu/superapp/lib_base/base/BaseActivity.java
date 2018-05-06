package org.gmfbilu.superapp.lib_base.base;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.umeng.message.PushAgent;

import org.gmfbilu.superapp.lib_base.app.ActivitiesManager;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by gmfbilu on 2018/3/7.
 */

public abstract class BaseActivity extends SupportActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(getClass().getName() + "---> onCreate");
        int layout = setLayout();
        if (layout != 0)
            setContentView(layout);
        findViewById_setOnClickListener(savedInstanceState);
        activityManager();
        initPush();
    }

    public abstract void findViewById_setOnClickListener(Bundle savedInstanceState);

    public abstract int setLayout();

    private void activityManager() {
        ActivitiesManager.addActivity(this, getClass());
    }

    private void initPush() {
        try {
            PushAgent.getInstance(this).onAppStart();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义通知打开页面后关闭此Activity
     */
    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
       // Logger.d(getClass().getName() + "---> onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Logger.d(getClass().getName() + "---> onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Logger.d(getClass().getName() + "---> onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();
        //Logger.d(getClass().getName() + "---> onStop");
    }

    @Override
    protected void onDestroy() {
        ActivitiesManager.removeActivity(this);
        super.onDestroy();
        Logger.d(getClass().getName() + "---> onDestroy");
    }
}
