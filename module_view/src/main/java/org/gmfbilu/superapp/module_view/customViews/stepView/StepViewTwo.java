package org.gmfbilu.superapp.module_view.customViews.stepView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class StepViewTwo extends BaseView {

    private Paint mPaint;
    private TextPaint mTextPaint;
    private ArrayList<StepViewTwoBean> data;

    //圆圈的半径
    private int mRadius;
    //圆圈和下面直线之间的间隙
    private int mCircle_line_gap;
    //圆圈的个数
    private int mCircleSize;
    //直线的个数
    private int mLineSize;
    //直线的高度
    private int mLineHeight;
    //圆圈和右边
    private int circle_text_gap;
    //直线的宽度
    private int lineWidth;
    //每个圆圈坐标点的集合
    private int[][] circlesLocations;
    //每个直线矩形的坐标集合
    private int[][] linesLocations;

    public StepViewTwo(Context context) {
        this(context, null);
    }

    public StepViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = AppUtils.dp2px(4);
        mCircle_line_gap = AppUtils.dp2px(2);
        mLineHeight = AppUtils.dp2px(64);
        circle_text_gap = AppUtils.dp2px(10);
        lineWidth = AppUtils.dp2px(2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#9B9B9B"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(AppUtils.sp2px(12));
        mTextPaint.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = _mScreenWidth;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mRadius * 2 * mCircleSize + mLineHeight * mLineSize + mCircle_line_gap * mLineSize * 2 + 200;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) return;
        for (int i = 0; i < mCircleSize; i++) {
            StepViewTwoBean stepViewTwoBean = data.get(i);
            //圆圈X坐标
            circlesLocations[i][0] = _mPaddingLeft + mRadius;
            //圆圈Y坐标
            circlesLocations[i][1] = _mPaddingTop + mRadius + i * (2 * mCircle_line_gap + 2 * mRadius + mLineHeight);
            //绘制圆圈
            mPaint.setColor(stepViewTwoBean.highlight ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            canvas.drawCircle(circlesLocations[i][0], circlesLocations[i][1], mRadius, mPaint);
            if (i < mLineSize) {
                //绘制矩形直线
                //直线矩形左上角坐标
                linesLocations[i][0] = circlesLocations[i][0] - lineWidth / 2;
                linesLocations[i][1] = circlesLocations[i][1] + mRadius + mCircle_line_gap;
                //直线矩形右下角坐标
                linesLocations[i][2] = linesLocations[i][0] + lineWidth;
                linesLocations[i][3] = linesLocations[i][1] + mLineHeight;
                mPaint.setColor(Color.parseColor("#9B9B9B"));
                canvas.drawRect(linesLocations[i][0], linesLocations[i][1], linesLocations[i][2], linesLocations[i][3], mPaint);
            }
            //绘制圆圈右边的文字
            // TODO: 2019/1/3 有bug ，暂时只能考虑一行的情况
            mTextPaint.setColor(stepViewTwoBean.highlight ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            String location = stepViewTwoBean.location;
            int stringHeight = AppUtils.getStringHeight(location, mTextPaint);
            int Xtext = mRadius * 2 + circle_text_gap + _mPaddingLeft;
            int Ytext = _mPaddingTop + stringHeight + i * (2 * mRadius + 2 * mCircle_line_gap + mLineHeight);
            canvas.drawText(location, Xtext, Ytext, mTextPaint);

            mTextPaint.setColor(stepViewTwoBean.highlight ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            mTextPaint.setTextSize(AppUtils.sp2px(11));
            String time = stepViewTwoBean.time;
            int stringHeight1 = AppUtils.getStringHeight(time, mTextPaint);
            canvas.drawText(time, Xtext, Ytext + stringHeight1 + AppUtils.dp2px(10), mTextPaint);
        }
    }

    public void setData(ArrayList<StepViewTwoBean> d) {
        data = d;
        mCircleSize = data.size();
        mLineSize = mCircleSize - 1;
        circlesLocations = new int[mCircleSize][2];
        linesLocations = new int[mLineSize][4];
        //requestLayout会重新测量
        requestLayout();
    }
}
