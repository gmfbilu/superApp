package org.gmfbilu.superapp.module_view.generalviews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import org.gmfbilu.superapp.module_view.R;

/**
 * Created by gmfbilu on 2018/3/23.
 */

public class CircleProgressBar extends View {

    /**
     * 上下文
     */
    private Context mContext;
    /**
     * view默认宽高，就是设置wrap_content的时候
     */
    private int mViewDefaultWidth = dp2px(200), mViewDefaultHeight = dp2px(200);
    /**
     * 兼容padding
     */
    private boolean padding;
    /**
     * view中心点
     */
    private float centerX, centerY;
    //view半径
    private float radius;
    private float mProgress, currentProgress;
    /**
     * 两个画笔
     */
    private Paint circleBgPaint, progressPaint;
    /**
     * 内外圆颜色
     */
    private int circleBgColor = 0xFFe1e5e8, progressColor = 0xFFf66b12;
    /**
     * 内外圆宽度
     */
    private int circleBgWidth = dp2px(10), progressWidth = dp2px(10);
    /**
     * view矩形
     */
    private RectF mRectF = new RectF();
    /**
     * 绘制动画
     */
    private ValueAnimator progressAnimator;
    private int duration = 1000;
    private boolean isDrawCenterProgressText = true;
    private Paint centerProgressTextPaint;
    private ProgressListener mProgressListener;

    /**
     * 给定接口回调
     */
    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        /**
         * 设置自定义属性
         */
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.module_view_CircleProgressBar);
        progressWidth = typedArray.getDimensionPixelOffset(R.styleable.module_view_CircleProgressBar_module_view_progressWidth, progressWidth);
        progressColor = typedArray.getColor(R.styleable.module_view_CircleProgressBar_module_view_progressColor, progressColor);
        duration = typedArray.getInteger(R.styleable.module_view_CircleProgressBar_module_view_circleAnimationDuration, duration);
        isDrawCenterProgressText = typedArray.getBoolean(R.styleable.module_view_CircleProgressBar_module_view_isDrawCenterProgressText, isDrawCenterProgressText);
        typedArray.recycle();

        /**
         * 初始化3个画笔
         */
        circleBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔宽度
        circleBgPaint.setStrokeWidth(circleBgWidth);
        circleBgPaint.setColor(circleBgColor);
        circleBgPaint.setAntiAlias(true);
        //笔头为半圆形
        circleBgPaint.setStrokeCap(Paint.Cap.ROUND);
        //填充模式，填充画笔的宽度
        circleBgPaint.setStyle(Paint.Style.STROKE);

        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setStyle(Paint.Style.STROKE);

        centerProgressTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        centerProgressTextPaint.setTextSize(sp2px(14));
        centerProgressTextPaint.setColor(Color.BLACK);
        //代表所要绘制文字所在矩形的相对位置,默认是Paint.Align.LEFT,Paint.Align.CENTER表示绘制文字的baseLine的x,y坐标位于文字长度的中间
        centerProgressTextPaint.setTextAlign(Paint.Align.CENTER);
        centerProgressTextPaint.setAntiAlias(true);
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
            setMeasuredDimension(mViewDefaultWidth, mViewDefaultHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mViewDefaultWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mViewDefaultHeight);
        }
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
         * 确定view中心点
         */
        centerX = w / 2;
        centerY = h / 2;
        /**
         * 确定半径
         */
        radius = Math.min(w, h) / 2 - Math.max(circleBgWidth, progressWidth);
        /**
         * 四个坐标点相对于View控件
         * 确定内矩形区域
         */
        mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!padding) {
            final int paddingLeft = getPaddingLeft();
            final int paddingRight = getPaddingRight();
            final int paddingTop = getPaddingTop();
            final int paddingBottom = getPaddingBottom();
            padding = true;
            /**
             * view内容部分宽高
             */
            int height = getHeight() - paddingTop - paddingBottom;
            int width = getWidth() - paddingLeft - paddingRight;
        }

        /**
         * 绘制背景圆
         * 内圆大小就是半径大小
         * 外圆大小就是半径大小加画笔宽度
         */
        canvas.drawCircle(centerX, centerY, radius, circleBgPaint);
        /**
         * 绘制圆弧
         * 圆弧所在的矩形，开始角度，扫过多少角度，是否画成扇形
         */
        canvas.drawArc(mRectF, 90, currentProgress, false, progressPaint);
        if (isDrawCenterProgressText) {
            Paint.FontMetricsInt fontMetricsInt = centerProgressTextPaint.getFontMetricsInt();
            /**
             * 确定文字基线,也就是y轴坐标
             */
            int baseline = (int) ((mRectF.bottom + mRectF.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2);
            /**
             * 文字绘制到整个布局的中心位置
             * 绘制原点x坐标
             * 绘制原点y坐标
             */
            canvas.drawText(currentProgress + "%", mRectF.centerX(), baseline, centerProgressTextPaint);
        }
    }

    private void initAnimation() {
        /**
         * 指定值的变化范围
         */
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        // 动画延时启动时间
        progressAnimator.setStartDelay(0);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(valueAnimator -> {
            /**
             * 获取当前值
             */
            float value = (float) valueAnimator.getAnimatedValue();
           // Logger.d("mProgress=" + mProgress + ", currentProgress=" + currentProgress+", value="+value);
            mProgress = value;
            currentProgress = value * 360 / 100;
            if (mProgressListener != null) {
                mProgressListener.currentProgressListener(roundTwo(value));
            }
            invalidate();
        });
    }


    public void startProgressAnimation() {
        progressAnimator.start();
    }

    public void pauseProgressAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && progressAnimator != null) {
            progressAnimator.pause();
        }
    }

    public void resumeProgressAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && progressAnimator != null) {
            progressAnimator.resume();
        }
    }

    public void stopProgressAnimation() {
        progressAnimator.end();
    }

    /**
     * 传入一个进度值，从0到progress动画变化
     *
     * @param progress
     * @return
     */
    public CircleProgressBar setProgressWithAnimation(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    /**
     * 实时进度，适用于下载进度回调时候之类的场景
     *
     * @param progress
     * @return
     */
    public CircleProgressBar setCurrentProgress(float progress) {
        mProgress = progress;
        currentProgress = progress * 360 / 100;
        invalidate();
        return this;
    }

    public CircleProgressBar setProgressListener(ProgressListener listener) {
        mProgressListener = listener;
        return this;
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
