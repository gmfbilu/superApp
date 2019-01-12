package org.gmfbilu.superapp.module_view.customViews.stepView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

import java.util.List;


public class StepViewOne extends BaseView {

    private List<StepViewOneBean> data;

    private Paint mPaint, mPathPaint;
    private TextPaint mTextPaint;

    //圆圈的半径
    private int radius;
    //圆圈和右边直线之间的间隙
    private int circle_line_gap;
    //圆圈和上方文字以及下方文字的距离
    private int circle_text_gap;
    //直线的高度
    private int lineHeight;


    //圆圈的个数
    private int circleSize;
    //直线的个数
    int lineSize;
    //直线的宽度
    private int lineWidth;
    //每个圆圈坐标点的集合
    private int[][] circlesLocations;
    //每个直线矩形的坐标集合
    private int[][] linesLocations;


    public StepViewOne(Context context) {
        this(context, null);
    }

    public StepViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = AppUtils.dp2px(4);
        circle_line_gap = AppUtils.dp2px(2);
        circle_text_gap = AppUtils.dp2px(10);
        lineHeight = AppUtils.dp2px(2);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(AppUtils.sp2px(12));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.parseColor("#35CBD1"));
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);
        mPathPaint.setStrokeWidth(AppUtils.sp2px(1));
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
            height = AppUtils.dp2px(100);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (data == null) return;
        for (int i = 0; i < circleSize; i++) {
            StepViewOneBean stepViewOneBean = data.get(i);
            //圆圈X坐标
            circlesLocations[i][0] = (2 * i + 1) * radius + i * lineWidth + 2 * i * circle_line_gap + _mPaddingLeft;
            //圆圈Y坐标
            circlesLocations[i][1] = (int) _mViewContentStartY + _mViewContentHeight / 2;
            //绘制圆圈
            mPaint.setColor(stepViewOneBean.status ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            canvas.drawCircle(circlesLocations[i][0], circlesLocations[i][1], radius, mPaint);
            //绘制圆圈上方文字以及背景
            if (stepViewOneBean.status) {
                mTextPaint.setColor(Color.parseColor("#ffffff"));
                int stringHeight = AppUtils.getStringHeight(stepViewOneBean.statusName, mTextPaint);
                int stringWidth = AppUtils.getStringWidth(stepViewOneBean.statusName, mTextPaint);
                //圆弧半径
                int arcRadi = stringHeight;
                canvas.save();
                Path path = new Path();
                int[] startPoint = {circlesLocations[i][0], circlesLocations[i][1] - radius - circle_text_gap};
                canvas.translate(startPoint[0], startPoint[1]);
                startPoint[0] = 0;
                startPoint[1] = 0;
                path.moveTo(startPoint[0], startPoint[1]);
                int[] pointOne = {startPoint[0] - AppUtils.dp2px(8), startPoint[1] - AppUtils.dp2px(8)};
                path.lineTo(pointOne[0], pointOne[1]);
                int[] pointTwo = {pointOne[0] + AppUtils.dp2px(8) - stringWidth / 2, pointOne[1]};
                path.lineTo(pointTwo[0], pointTwo[1]);
                path.arcTo(pointTwo[0] - arcRadi, pointTwo[1] - 2 * arcRadi, pointTwo[0] + arcRadi, pointTwo[1], -270, 180, false);
                path.lineTo(startPoint[0], pointTwo[1] - 2 * arcRadi);
                canvas.drawPath(path, mPathPaint);
                //进行Y轴对折
                canvas.scale(-1f, 1f, startPoint[0], 0);
                canvas.drawPath(path, mPathPaint);
                canvas.restore();
                mPaint.setColor(Color.parseColor("#35CBD1"));
                canvas.drawCircle(circlesLocations[i][0], circlesLocations[i][1], radius, mPaint);
                mTextPaint.setColor(Color.parseColor("#FFFFFF"));
                if (!StringUtils.isEmpty(stepViewOneBean.statusName)) {
                    canvas.drawText(stepViewOneBean.statusName, circlesLocations[i][0], circlesLocations[i][1] - radius - circle_text_gap - AppUtils.dp2px(10) - (arcRadi * 2 - stringHeight) / 2, mTextPaint);
                }
            } else {
                mTextPaint.setColor(Color.parseColor("#35CBD1"));
                canvas.drawText(stepViewOneBean.statusName, circlesLocations[i][0], circlesLocations[i][1] - circle_text_gap - radius, mTextPaint);
            }
            if (i < lineSize) {
                //直线矩形左上角坐标
                linesLocations[i][0] = circlesLocations[i][0] + radius + circle_line_gap;
                linesLocations[i][1] = circlesLocations[i][1] - lineHeight / 2;
                //直线矩形右下角坐标
                linesLocations[i][2] = linesLocations[i][0] + lineWidth;
                linesLocations[i][3] = circlesLocations[i][1] + lineHeight / 2;
                mPaint.setColor(Color.parseColor("#9B9B9B"));
                canvas.drawRect(linesLocations[i][0], linesLocations[i][1], linesLocations[i][2], linesLocations[i][3], mPaint);
            }
            mTextPaint.setColor(Color.parseColor("#666666"));
            String city = stepViewOneBean.city;
            if (!StringUtils.isEmpty(city)) {
                canvas.drawText(city, circlesLocations[i][0], circlesLocations[i][1] + circle_text_gap + AppUtils.getStringHeight(city, mTextPaint) + radius, mTextPaint);
            }
        }
    }


    public void setData(List<StepViewOneBean> d) {
        data = d;
        circleSize = data.size();
        lineSize = circleSize - 1;
        lineWidth = (_mViewContentWidth - circleSize * 2 * radius - circle_line_gap * 2 * lineSize) / lineSize;
        circlesLocations = new int[circleSize][2];
        linesLocations = new int[lineSize][4];
        //invalidate只会触发onDraw方法
        invalidate();
    }
}
