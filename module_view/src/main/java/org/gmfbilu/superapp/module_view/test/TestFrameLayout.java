package org.gmfbilu.superapp.module_view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TestFrameLayout extends FrameLayout {


    public TestFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public TestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestFrameLayout dispatchTouchEvent "+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestFrameLayout onInterceptTouchEvent "+super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtil.e("TestFrameLayout onTouchEvent "+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
