package org.gmfbilu.superapp.module_view.customViews.canvasView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

public class CanvasViewTestOne extends BaseView {


    //外层圆半径
    private int radious;
    //外层圆中心点坐标
    private float[] circleLocation = new float[2];
    //指针长度
    private int pointerLengthBig, pointerLengthSmall;
    private Paint circlePaint, keduPaint, hourPaint, minutePaint;


    public CanvasViewTestOne(Context context) {
        this(context, null);
    }

    public CanvasViewTestOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasViewTestOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radious = AppUtils.dp2px(100);
        pointerLengthBig = radious / 4;
        pointerLengthSmall = pointerLengthBig * 2 / 3;

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.parseColor("#000000"));
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.STROKE);

        keduPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        keduPaint.setStrokeWidth(3);
        keduPaint.setColor(Color.parseColor("#000000"));
        keduPaint.setStyle(Paint.Style.STROKE);

        //画2根指针
        hourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hourPaint.setColor(Color.parseColor("#000000"));
        hourPaint.setStrokeWidth(20);
        hourPaint.setStyle(Paint.Style.STROKE);

        minutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        minutePaint.setColor(Color.parseColor("#000000"));
        minutePaint.setStrokeWidth(10);
        minutePaint.setStyle(Paint.Style.STROKE);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(AppUtils.dp2px(200), AppUtils.dp2px(200));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circleLocation[0] = _mViewContentStartX + radious;
        circleLocation[1] = _mViewContentStartY + radious;
        int gap = AppUtils.dp2px(4);
        canvas.drawCircle(circleLocation[0], circleLocation[1], radious, circlePaint);
        //刻度线
        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9) {
                keduPaint.setStrokeWidth(6);
                keduPaint.setTextSize(AppUtils.sp2px(15));
                canvas.drawLine(circleLocation[0], pointerLengthBig, circleLocation[0], 0, keduPaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree, circleLocation[0] - AppUtils.getStringWidth(degree, keduPaint) / 2, pointerLengthBig + AppUtils.getStringHeight(degree, keduPaint) + gap, keduPaint);
            } else {
                keduPaint.setStrokeWidth(3);
                keduPaint.setTextSize(AppUtils.sp2px(13));
                canvas.drawLine(circleLocation[0], pointerLengthSmall, circleLocation[0], 0, keduPaint);
                String degree = String.valueOf(i);
                canvas.drawText(degree, circleLocation[0] - AppUtils.getStringWidth(degree, keduPaint) / 2, pointerLengthSmall + AppUtils.getStringHeight(degree, keduPaint) + gap, keduPaint);

            }
            canvas.rotate(30, _mViewWidth / 2, _mViewHeight / 2);
        }
        canvas.save();
        //将坐标系平移到圆点
        canvas.translate(_mViewWidth / 2, _mViewHeight / 2);
        canvas.drawLine(0, 0, 100, 100, hourPaint);
        canvas.drawLine(0, 0, 100, 150, minutePaint);
        canvas.restore();
    }
}
