package org.gmfbilu.superapp.module_view.customViews.pathView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

import androidx.annotation.Nullable;


/**
 * 在AndroidMainfest文件中application节点下添上 android:hardwareAccelerated=”false”以关闭整个应用的硬件加速
 * <p>
 * Path封装了由直线和曲线(二次，三次贝塞尔曲线)构成的几何路径
 * <p>
 * moveTo只改变下次操作的起点，不影响之前的操作。setLastPoint是重置上一次操作的最后一个点，影响之前的操作
 * <p>
 * Direction的意思是方向，趋势。CW是顺时针，CCW是逆时针。在添加图形时确定闭合顺序(各个点的记录顺序)，对图形的渲染结果有影响(是判断图形渲染的重要条件)
 * <p>
 * addPath (Path src),将两个Path合并成为一个。addPath (Path src, Matrix matrix)，将src添加到当前path之前先使用Matrix进行变换。addPath (Path src, float dx, float dy)，将src进行了位移之后再添加进当前path中
 * <p>
 * addArc是直接添加一个圆弧到path中。arcTo是添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
 * <p>
 * set (Path src)，新的path赋值到现有path
 * <p>
 * offset (float dx, float dy)，对path进行一段平移，path的offset只作用于当前path。
 * <p>
 * path1.op(path2, Path.Op.DIFFERENCE);对path1和path2执行布尔运算，运算方式由第二个参数指定，运算结果存入到path1中。Path的布尔运算有五种逻辑:
 * DIFFERENCE:差集，Path1中减去Path2后剩下的部分
 * REVERSE_DIFFERENCE：差集，Path2中减去Path1后剩下的部分
 * INTERSECT：交集，Path1与Path2相交的部分
 * UNION：并集，包含全部Path1和Path2
 * XOR	异或	包含Path1与Path2但不包括两者相交的部分
 */
public class PathView extends BaseView {

    private Paint mPaint;
    private Point start, end, control;

    private Paint mCpaint;
    private int mCradius;
    private float c = 0.551915024494f;
    //类圆形的四个数据点，8个控制点。组成四个类半圆，四个三次贝赛尔曲线
    private Point p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11;
    private float mAnimatedValue;
    //临界值,弹性延长的最大值
    int r2;
    //各个阶段所占用的时间比，加起来是1
    private float STAGE_1 = 0.3F;
    private float STAGE_2 = 0.5F;
    private float STAGE_3 = 0.2F;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);

        control = new Point(250, 100);
        start = new Point(0, 500);
        end = new Point(400, 500);

        mCpaint = new Paint();
        mCpaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mCpaint.setAntiAlias(true);
        mCpaint.setColor(Color.RED);
        mCpaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCradius = AppUtils.dp2px(20);
        r2 = 2 * mCradius;

        p0 = new Point(0, 0);
        p1 = new Point(0, 0);
        p11 = new Point(0, 0);

        p3 = new Point(0, mCradius);
        p2 = new Point(0, 0);
        p4 = new Point(0, 0);

        p6 = new Point(0, 0);
        p5 = new Point(0, 0);
        p7 = new Point(0, 0);

        p9 = new Point(0, -mCradius);
        p8 = new Point(0, 0);
        p10 = new Point(0, 0);
        startAnimator();

    }

    private void startAnimator() {
        mAnimatedValue = 0;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
                if (mAnimatedValue == 1) {
                    startAnimator();
                }
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(20000);
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(AppUtils.getScreenWidth(), AppUtils.dp2px(150));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBezier(canvas);
        drawCircle(canvas);

    }

    private void drawBezier(Canvas canvas) {
        //辅助线
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(AppUtils.dp2px(1));
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);
        //控制点和数据点
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(AppUtils.dp2px(5));
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        //二次贝赛尔曲线
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(AppUtils.dp2px(2));
        mPaint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(path, mPaint);
    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        //下半部分
        if (mAnimatedValue < STAGE_1) {
            //第一阶段，小球不动，只是右边拉伸到最大值r2，拉伸速率相对比较慢
            canvas.translate(mCradius, _mViewContentHeight / 2);
            //半径变化量
            int r = (int) (mCradius * (1 + mAnimatedValue * STAGE_1 * 10));
            //左半球不变
            p0.x = -mCradius;
            p0.y = 0;
            p1.x = -mCradius;
            p1.y = (int) (mCradius * c);
            p2.x = -(int) (mCradius * c);
            p2.y = mCradius;
            p11.x = -mCradius;
            p11.y = -(int) (mCradius * c);
            p10.x = -(int) (mCradius * c);
            p10.y = -mCradius;

            //右半球变
            //x变，y不变
            p4.x = (int) (r * c);
            p4.y = mCradius;
            //x变，y不变
            p5.x = r;
            p5.y = (int) (mCradius * c);
            p6.x = r;
            p6.y = 0;
            //x变，y不变
            p7.x = r;
            p7.y = -(int) (mCradius * c);
            //x变，y不变
            p8.x = (int) (r * c);
            p8.y = -mCradius;
        } else if (mAnimatedValue < STAGE_1 + STAGE_2) {
            //长度变化量
            float b = (_mViewContentWidth - mCradius * 2) * (mAnimatedValue - STAGE_1) / STAGE_2;
            //小球移动
            canvas.translate(mCradius + b, _mViewContentHeight / 2);
            //半径变化量,左半球变化量要快，时间是第二阶段总时间的三分之一
            float changeTime = STAGE_2 / 3;
            int rLeft;
            int rRight;
            if (mAnimatedValue - STAGE_1 - changeTime < 0) {
                //左半球拉伸阶段
                rLeft = (int) ((mAnimatedValue - STAGE_1) * mCradius / changeTime + mCradius);
                rRight = r2;
            } else if (mAnimatedValue - STAGE_1 - changeTime * 2 < 0) {
                //整个球处于拉伸完成阶段，整体保持不变
                rLeft = r2;
                rRight = r2;
            } else {
                //整个球处于拉伸完成阶段，然后右半球开始收缩，左半球不变
                rLeft = r2;
                float surplusTime = mAnimatedValue - STAGE_1 - changeTime * 2;
                rRight = (int) (r2 - surplusTime * mCradius * 3 * (1 / STAGE_2));
            }
            //左边球变
            p0.x = -rLeft;
            p0.y = 0;
            p1.x = -rLeft;
            p1.y = (int) (mCradius * c);
            p2.x = -(int) (rLeft * c);
            p2.y = mCradius;
            p11.x = -rLeft;
            p11.y = -(int) (mCradius * c);
            p10.x = -(int) (rLeft * c);
            p10.y = -mCradius;

            //右半球不变
            //x变，y不变
            p4.x = (int) (rRight * c);
            p4.y = mCradius;
            //x变，y不变
            p5.x = rRight;
            p5.y = (int) (mCradius * c);
            p6.x = rRight;
            p6.y = 0;
            //x变，y不变
            p7.x = rRight;
            p7.y = -(int) (mCradius * c);
            //x变，y不变
            p8.x = (int) (rRight * c);
            p8.y = -mCradius;
        } else {
            canvas.translate(_mViewContentWidth - mCradius, _mViewContentHeight / 2);
            //半径变小,最大是2r
            //半径变化量
            int r = (int) (mCradius + (1 - mAnimatedValue) * (1 / STAGE_3) * mCradius);
            //左边球变
            p0.x = -r;
            p0.y = 0;
            p1.x = -r;
            p1.y = (int) (mCradius * c);
            p2.x = -(int) (r * c);
            p2.y = mCradius;
            p11.x = -r;
            p11.y = -(int) (mCradius * c);
            p10.x = -(int) (r * c);
            p10.y = -mCradius;

            //右半球不变
            p4.x = (int) (mCradius * c);
            p4.y = mCradius;
            p5.x = mCradius;
            p5.y = (int) (mCradius * c);
            p6.x = mCradius;
            p6.y = 0;
            p7.x = mCradius;
            p7.y = -(int) (mCradius * c);
            p8.x = (int) (mCradius * c);
            p8.y = -mCradius;

        }
        Path path1 = new Path();
        path1.moveTo(p0.x, p0.y);
        path1.cubicTo(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
        canvas.drawPath(path1, mCpaint);
        Path path2 = new Path();
        path2.moveTo(p3.x, p3.y);
        path2.cubicTo(p4.x, p4.y, p5.x, p5.y, p6.x, p6.y);
        canvas.drawPath(path2, mCpaint);
        Path path3 = new Path();
        path3.moveTo(p0.x, p0.y);
        path3.lineTo(p3.x, p3.y);
        path3.lineTo(p6.x, p6.y);
        path3.close();
        canvas.drawPath(path3, mCpaint);
        //上半部分
        Path path4 = new Path();
        path4.moveTo(p0.x, p0.y);
        path4.cubicTo(p11.x, p11.y, p10.x, p10.y, p9.x, p9.y);
        canvas.drawPath(path4, mCpaint);
        Path path5 = new Path();
        path5.moveTo(p9.x, p9.y);
        path5.cubicTo(p8.x, p8.y, p7.x, p7.y, p6.x, p6.y);
        canvas.drawPath(path5, mCpaint);
        Path path6 = new Path();
        path6.moveTo(p0.x, p0.y);
        path6.lineTo(p9.x, p9.y);
        path6.lineTo(p6.x, p6.y);
        path6.close();
        canvas.drawPath(path6, mCpaint);
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        if (x > _mScreenWidth / 2) {
            return super.onTouchEvent(event);
        }
        // 根据触摸位置更新控制点，并提示重绘
        control.x = x;
        control.y = (int) event.getY();
        invalidate();
        return true;
    }


}
