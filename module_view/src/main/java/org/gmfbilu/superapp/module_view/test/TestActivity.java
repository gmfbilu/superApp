package org.gmfbilu.superapp.module_view.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;

import androidx.annotation.Nullable;

/**
 * Activity没有继承View或ViewGroup，自己实现dispatchTouchEvent和onTouchEvent。
 * 其中onTouchEvent在dispatchTouchEvent中被调用。
 * Activity的子类都没有重写这两个方法
 *
 * ViewGroup没有实现onTouchEvent，自己实现了onInterceptTouchEvent和dispatchTouchEvent。
 * 其中onInterceptTouchEvent在dispatchTouchEvent中被调用。
 * ViewGroup的几个布局子类都没有重写这三个方法。
 *
 * View自己实现dispatchTouchEvent和onTouchEvent。
 * 其中onTouchEvent在dispatchTouchEvent中被调用。
 * TextView只重写了onTouchEvent。ImageView都没有重写这两个方法。
 */
public class TestActivity extends Activity {


    private TestButton bt_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_view_activity_test);
        bt_click=findViewById(R.id.bt_click);
        bt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        bt_click.performClick();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestActivity dispatchTouchEvent "+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtil.e("TestActivity onTouchEvent "+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }


}
