package org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.自定义控件;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 小船进度条
 */
public class ProgressView extends View {

    private float mMaxProgress;
    private float mCurrentProgress;
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
