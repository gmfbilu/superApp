package org.gmfbilu.superapp.module_view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import androidx.annotation.Nullable;

public class TestLinearLayout extends LinearLayout {


    public TestLinearLayout(Context context) {
        this(context,null);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestLinearLayout dispatchTouchEvent "+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestLinearLayout onInterceptTouchEvent "+super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtil.e("TestLinearLayout onTouchEvent "+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
