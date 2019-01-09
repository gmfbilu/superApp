package org.gmfbilu.superapp.module_view.stepView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;

/**
 * 自定义View需要处理padding，无需处理margin。自定义ViewGroup需要同时处理padding和margin
 */
public class StepViewOne extends View {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * view默认宽高，就是设置wrap_content的时候，默认宽是屏幕宽度，高40dp
     * 这种有问题，就是设置wrap_content的时候，View的宽高限制死了，不能随着View内容的大小改变而改变
     */
    private int mViewDefaultWidth, mViewDefaultHeight;
    /**
     * View内容宽高
     */
    private int viewContentWidth, viewContentHeight;
    /**
     * View宽高
     */
    private int viewWidth, viewHeight;
    /**
     * view中心点
     */
    private float centerX, centerY;
    /**
     * padding
     */
    private int paddingLeft, paddingRight, paddingTop, paddingBottom;


    private String[] processName = {"已发货", "运输中", "派送中", "已签收"};
    private String[] cities = {"北京市", "深圳市"};

    private Paint mPaint, mPathPaint;
    private TextPaint mTextPaint;


    public StepViewOne(Context context) {
        this(context, null);
    }

    public StepViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mViewDefaultWidth = AppUtils.getScreenWidth(mContext);
        mViewDefaultHeight = AppUtils.dp2px(mContext, 100);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(AppUtils.sp2px(mContext, 12));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.parseColor("#35CBD1"));
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);
        mPathPaint.setStrokeWidth(AppUtils.sp2px(mContext, 1));

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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);   //获取宽的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec); //获取高的模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);   //获取宽的尺寸
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //获取高的尺寸
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果match_parent或者具体的值，直接赋值
            width = widthSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸。这里假设充满屏幕的宽度
            float viewContentWidth = AppUtils.getScreenWidth(mContext);
            //控件的宽度就是文本的宽度加上两边的内边距。内边距就是padding值，在构造方法执行完就被赋值
            width = (int) (paddingLeft + viewContentWidth + paddingRight);
        }
        //高度跟宽度处理方式一样
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果是wrap_content，我们要得到控件需要多大的尺寸。这里假设一个高度
            //控件的高度就是View内容的高度加上上下的内边距。内边距就是padding值
            height =paddingTop + paddingBottom +mViewDefaultHeight ;
        }
        //保存测量宽度和测量高度
        setMeasuredDimension(width, height);
    }


    /**
     * 确定大小
     * view宽高就是h和w
     * 当控件设置wrap_content的时候view宽高就是默认宽高mViewDefaultWidth和mViewDefaultHeight，和padding，margin无关
     * 当控件设置具体大小的时候view宽高就是具体大小的宽高，和padding，margin无关
     * getHeight()和getWidth()就是view宽高也就是h和w
     *
     * @param w    当前view的宽高
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        /**
         * 确定view中心点
         */
        centerX = w / 2;
        centerY = h / 2;
        /**
         * padding就是设置的padding
         */
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();

        /**
         * view顶点坐标(viewLeft,viewTop),(viewRight,viewBottom),是个矩形区域。坐标点和margin有关
         * viewBottom-viewTop=h
         * viewRight-viewLeft=w
         */
        int viewLeft = getLeft();
        int viewTop = getTop();
        int viewRight = getRight();
        int viewBottom = getBottom();

        /**
         * view的内容部分宽高
         */
        viewContentHeight = h - paddingTop - paddingBottom;
        viewContentWidth = w - paddingLeft - paddingRight;

        //四个坐标点相对于父控件
        Logger.d("w=" + w + ", h=" + h + "\n" +
                "viewContentWidth=" + viewContentWidth + ", viewContentHeight=" + viewContentHeight + "\n" +
                "viewLeft=" + viewLeft + ", viewTop=" + viewTop + ", viewRight=" + viewRight + ", viewBottom=" + viewBottom + "\n " +
                "paddingLeft=" + paddingLeft + "\n" +
                "paddingRight=" + paddingRight + "\n" +
                "paddingTop=" + paddingTop + "\n" +
                "paddingBottom=" + paddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆圈的半径
        int radius = AppUtils.dp2px(mContext, 4);
        //圆圈和右边直线之间的间隙
        int circle_line_gap = AppUtils.dp2px(mContext, 2);
        //圆圈和上方文字以及下方文字的距离
        int circle_text_gap = AppUtils.dp2px(mContext, 10);
        //圆圈的个数
        int circleSize = processName.length;
        //直线的个数
        int lineSize = circleSize - 1;
        //直线的高度
        int lineHeight = AppUtils.dp2px(mContext, 2);
        //直线的宽度
        int lineWidth = (viewContentWidth - circleSize * 2 * radius - circle_line_gap * 2 * lineSize) / 3;
        //每个圆圈坐标点的集合
        int[][] circlesLocations = new int[circleSize][2];
        //每个直线矩形的坐标集合
        int[][] linesLocations = new int[lineSize][4];
        // TODO: 2019/1/3 模拟数据
        int status = 2;
        for (int i = 0; i < circleSize; i++) {
            //圆圈X坐标
            circlesLocations[i][0] = (2 * i + 1) * radius + i * lineWidth + 2 * i * circle_line_gap + paddingLeft;
            //圆圈Y坐标
            circlesLocations[i][1] = (int) centerY + paddingTop;
            //绘制圆圈
            mPaint.setColor(i == status ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            canvas.drawCircle(circlesLocations[i][0], circlesLocations[i][1], radius, mPaint);
            //绘制圆圈上方文字以及背景
            String process = processName[i];
            if (i == status) {
                mTextPaint.setColor(Color.parseColor("#ffffff"));
                int stringHeight = AppUtils.getStringHeight(process, mTextPaint);
                int stringWidth = AppUtils.getStringWidth(process, mTextPaint);
                //圆弧半径
                int arcRadi = stringHeight;
                canvas.save();
                Path path = new Path();
                int[] startPoint = {circlesLocations[i][0], circlesLocations[i][1] - radius - circle_text_gap};
                canvas.translate(startPoint[0], startPoint[1]);
                startPoint[0] = 0;
                startPoint[1] = 0;
                path.moveTo(startPoint[0], startPoint[1]);
                int[] pointOne = {startPoint[0] - AppUtils.dp2px(mContext, 8), startPoint[1] - AppUtils.dp2px(mContext, 8)};
                path.lineTo(pointOne[0], pointOne[1]);
                int[] pointTwo = {pointOne[0] + AppUtils.dp2px(mContext, 8) - stringWidth / 2, pointOne[1]};
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
                canvas.drawText(process, circlesLocations[i][0], circlesLocations[i][1] - radius - circle_text_gap - AppUtils.dp2px(mContext, 10) - (arcRadi * 2 - stringHeight) / 2, mTextPaint);
            } else {
                mTextPaint.setColor(Color.parseColor("#35CBD1"));
                canvas.drawText(process, circlesLocations[i][0], circlesLocations[i][1] - circle_text_gap - radius, mTextPaint);
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
            //绘制圆圈下方文字
            if (i == 0 || i == circleSize - 1) {
                mTextPaint.setColor(Color.parseColor("#666666"));
                String city = i == 0 ? cities[0] : cities[1];
                canvas.drawText(city, circlesLocations[i][0], circlesLocations[i][1] + circle_text_gap + AppUtils.getStringHeight(city, mTextPaint) + radius, mTextPaint);
            }
        }
    }

}
