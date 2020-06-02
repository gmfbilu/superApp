package org.gmfbilu.superapp.module_view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

public class TestButton extends androidx.appcompat.widget.AppCompatButton {


    public TestButton(Context context) {
        this(context,null);
    }

    public TestButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LoggerUtil.e("TestButton dispatchTouchEvent "+super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LoggerUtil.e("TestButton onTouchEvent "+super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }


}
