package org.gmfbilu.superapp.module_view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import androidx.constraintlayout.widget.ConstraintLayout;

public class TestConstraintLayout extends ConstraintLayout {


    public TestConstraintLayout(Context context) {
        this(context,null);
    }

    public TestConstraintLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestConstraintLayout dispatchTouchEvent "+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestConstraintLayout onInterceptTouchEvent "+super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtil.e("TestConstraintLayout onTouchEvent "+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }
}
