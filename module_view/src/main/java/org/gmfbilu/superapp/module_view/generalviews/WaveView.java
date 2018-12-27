package org.gmfbilu.superapp.module_view.generalviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by gmfbilu on 18-3-18.
 * 波浪动画
 */

public class WaveView extends View {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 波浪画笔
     */
    private Paint mWavePaint;
    /**
     * 一个周期波浪到长度
     */
    private int mWaveLength;
    /**
     * 波浪路径
     */
    private Path mWavePath;
    /**
     * 平移偏移量
     */
    private int mOffset;
    /**
     * 一个屏幕内显示几个周期
     */
    private int mWaveCount;
    /**
     * 振幅
     */
    private int mWaveAmplitude;
    /**
     * 波浪到颜色
     */
    private int mWaveColor = 0xaaff7e3e;

    private static final int SIN = 0;
    private static final int COS = 1;
    private static final int DEFAULT = SIN;

    private int waveType = DEFAULT;


    private ValueAnimator mValueAnimator;


    public WaveView(Context context) {
        super(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mWaveAmplitude = dp2px(10);
        mWaveLength = dp2px(500);
        mWavePath = new Path();
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(mWaveColor);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mWavePaint.setAntiAlias(true);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenHeight = h;
        mScreenWidth = w;
        /**
         * 加上1.5是为了保证至少有两个波形（屏幕外边一个完整的波形，屏幕里边一个完整的波形）
         */
        mWaveCount = (int) Math.round(mScreenWidth / mWaveLength + 1.5);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (waveType) {
            case SIN:
                drawSinPath(canvas);
                break;
            case COS:
                break;
        }

    }

    /**
     * sin函数图像的波形
     *
     * @param canvas
     */
    private void drawSinPath(Canvas canvas) {
        mWavePath.reset();


    }

    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

}
