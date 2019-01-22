package org.gmfbilu.superapp.module_view.customViews.pathView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

/**
 * PathMeasure是一个用来测量Path的类
 * <p>
 * 构造方法:
 * PathMeasure():创建一个空的PathMeasure，使用之前需要先调用setPath方法来与Path进行关联。被关联的Path必须是已经创建好的，如果关联之后Path内容进行了更改，则需要使用setPath方法重新关联
 * PathMeasure(Path path, boolean forceClosed):创建PathMeasure并关联一个指定的Path(Path需要已经创建完成)，如果关联之后Path内容进行了更改，则需要使用setPath方法重新关联。第二个参数是用来确保Path闭合，如果设置为true， 则不论之前Path是否闭合，都会自动闭合该Path
 * <p>
 * 公共方法:
 * setPath(Path path, boolean forceClosed):关联一个Path
 * isClosed():是否闭合
 * getLength():获取Path的长度
 * nextContour():跳转到下一个轮廓
 * getSegment(float startD, float stopD, Path dst, boolean startWithMoveTo):截取片段
 * <p>
 * getPosTan(float distance, float[] pos, float[] tan):获取指定长度的位置坐标及该点切线值。
 * 返回值(boolean)判断获取是否成功，	true表示成功，数据会存入pos和tan中，false表示失败，pos和tan不会改变
 * distance是距离Path起点的长度	取值范围: 0 <= distance <= getLength
 * pos是该点的坐标值	当前点在画布上的位置，有两个数值分别为x，y坐标
 * tan是该点的正切值	当前点在曲线上的方向，使用Math.atan2(tan[1], tan[0])获取到正切角的弧度值。tan是用来判断Path上趋势的，即在这个位置上曲线的走向。tan[0]是邻边边长，tan[1]是对边边长
 * <p>
 * getMatrix(float distance, Matrix matrix, int flags):获取指定长度的位置坐标及该点Matrix
 * 返回值(boolean)判断获取是否成功	true表示成功，数据会存入matrix中，false失败，matrix内容不会改变
 * distance距离Path起点的长度	取值范围: 0 <= distance <= getLength
 * matrix根据falgs封装好的matrix会根据flags的设置而存入不同的内容
 * flags规定哪些内容会存入到matrix中	可选择PathMeasure.POSITION_MATRIX_FLAG(位置)和PathMeasure.ANGENT_MATRIX_FLAG(正切)
 */
public class PathMeasureView extends BaseView {

    private Paint mDeafultPaint;

    private float currentValue = 0;     // 用于纪录当前的位置,取值范围[0,1]映射Path的整个长度
    private float[] pos;                // 当前点的实际位置
    private float[] tan;                // 当前点的tangent值,用于计算图片所需旋转的角度
    private Bitmap mBitmap;             // 箭头图片
    private Matrix mMatrix;             // 矩阵,用于对图片进行一些操作

    private Paint searchPaint;
    private float mAnimatedValue;
    private float radius;
    private Path mCirclePath, mSearchPath;


    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDeafultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDeafultPaint.setColor(Color.BLACK);
        mDeafultPaint.setStrokeWidth(5);
        mDeafultPaint.setStyle(Paint.Style.STROKE);

        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;       // 缩放图片
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.path_measure_arror, options);
        mMatrix = new Matrix();

        searchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        searchPaint.setStrokeWidth(15);
        searchPaint.setStyle(Paint.Style.STROKE);

        radius = AppUtils.dp2px(30);
        mCirclePath = new Path();
        mSearchPath = new Path();
        startAnimator();
    }

    private void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(AppUtils.getScreenWidth(), AppUtils.dp2px(100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArror(canvas);
        drawSearch(canvas);
    }

    private void drawSearch(Canvas canvas) {
        canvas.save();
        canvas.translate(_mViewContentWidth / 2, _mViewContentHeight / 2);
        Path path1 = new Path();
        path1.addCircle(0, 0, radius, Path.Direction.CW);
        searchPaint.setColor(Color.TRANSPARENT);
        PathMeasure pathMeasure = new PathMeasure(path1, false);
        float[] pos = new float[2];
        float[] tan = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength()*mAnimatedValue,pos,tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI);
        canvas.drawPath(path1,searchPaint);
        searchPaint.setColor(Color.BLUE);
        canvas.restore();
    }


    private void drawArror(Canvas canvas) {
        canvas.save();
        canvas.translate(300, _mViewContentHeight / 2);      // 平移坐标系
        Path path = new Path();                                 // 创建 Path
        path.addCircle(0, 0, 200, Path.Direction.CW);           // 添加一个圆形
        PathMeasure measure = new PathMeasure(path, false);     // 创建 PathMeasure
        currentValue += 0.005;                                  // 计算当前的位置在总长度上的比例[0,1]
        if (currentValue >= 1) {
            currentValue = 0;
        }
        measure.getPosTan(measure.getLength() * currentValue, pos, tan);        // 获取当前位置的坐标以及趋势
        mMatrix.reset();                                                        // 重置Matrix
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180.0 / Math.PI); // 计算图片旋转角度
        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);   // 旋转图片
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合
        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

        //在onDraw里面调用了invalidate 方法来保持界面不断刷新，但并不提倡这么做，正确对做法应该是使用线程或者ValueAnimator来控制界面的刷新
        invalidate();                                                           // 重绘页面
        canvas.restore();
    }
}
