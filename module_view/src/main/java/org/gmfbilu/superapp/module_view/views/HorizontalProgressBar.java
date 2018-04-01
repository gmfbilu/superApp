package org.gmfbilu.superapp.module_view.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gmfbilu on 2018/3/27.
 */

public class HorizontalProgressBar extends View {

    private int mDefaultViewWidth = dp2px(300), mDefaultViewHeight = dp2px(40);
    //背景进度条，前景进度条
    private Rect bgRect = new Rect(), fgRect = new Rect();
    //背景进度条画笔,前景进度条画笔
    private Paint bgPaint, fgPaint;
    //进度条的宽高
    private int mBgHPheight = dp2px(20), mBgHPwidth = mDefaultViewWidth;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(Color.GRAY);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }


    /**
     * 确定大小
     *
     * @param w    当前view的宽高
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        /**
         * view宽高
         */
        int viewHeight = h;
        int viewWidth = w;

        /**
         * view顶点坐标(viewLeft,viewTop),(viewRight,viewBottom)
         */
        int viewLeft = getLeft();
        int viewTop = getTop();
        int viewRight = getRight();
        int viewBottom = getBottom();

        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        /**
         * view的内容部分宽高
         */
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        int contentWidth = getWidth() - paddingLeft - paddingRight;

        /**
         * view的内容顶点坐标(contentLeft,contentTop),(contentRight,contentBottom)
         */
        int contentLeft = viewLeft + paddingLeft;
        int contentTop = viewTop + paddingTop;
        int contentRight = viewRight - paddingRight;
        int contentBottom = viewBottom - paddingBottom;

        //四个坐标点相对于父控件
        com.orhanobut.logger.Logger.d("viewHeight=" + viewHeight + ", viewWidth=" + viewWidth + "\n" +
                "contentHeight=" + contentHeight + ", contentWidth=" + contentWidth + "\n" +
                "viewLeft=" + viewLeft + ", viewTop=" + viewTop + ", viewRight=" + viewRight + ", viewBottom=" + viewBottom + "\n " +
                "contentLeft=" + contentLeft + ", contentTop=" + contentTop + ", contentRight=" + contentRight + ", contentBottom=" + contentBottom + "\n " +
                "paddingLeft=" + paddingLeft);
        bgRect.set(contentLeft, contentTop, contentRight, contentBottom);
    }


    /**
     * 设置默认view大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultViewWidth, mDefaultViewHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mDefaultViewWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mDefaultViewHeight);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制背景
         */
        canvas.drawRect(bgRect, bgPaint);
    }


    /**
     * 将一个小数四舍五入，保留两位小数返回
     *
     * @param originNum
     * @return
     */
    public static float roundTwo(float originNum) {
        return (float) (Math.round(originNum * 10) / 10.00);
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }


}
