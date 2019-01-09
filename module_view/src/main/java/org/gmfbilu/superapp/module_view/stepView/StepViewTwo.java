package org.gmfbilu.superapp.module_view.stepView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;

import java.util.ArrayList;

public class StepViewTwo extends View {
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


    private Paint mPaint;
    private TextPaint mTextPaint;
    private ArrayList<StepViewTwoBean> mStepViewTwoBeans;
    private int mRadius;
    private int mCircle_line_gap;
    private int mCircleSize;
    private int mLineSize;
    private int mLineHeight;

    public StepViewTwo(Context context) {
        this(context, null);
    }

    public StepViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mViewDefaultWidth = AppUtils.getScreenWidth(mContext);
        mViewDefaultHeight = AppUtils.dp2px(mContext, 100);


        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#9B9B9B"));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(AppUtils.sp2px(mContext, 12));
        mTextPaint.setAntiAlias(true);

        mStepViewTwoBeans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            StepViewTwoBean stepViewTwoBean = new StepViewTwoBean();
            if (i == 0) {
                stepViewTwoBean.highlight = true;
            }
            stepViewTwoBean.location = "到达目的地网点广东深圳公司，快件将很快进aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
            stepViewTwoBean.time = "2018-11-25 12:00:00";
            mStepViewTwoBeans.add(stepViewTwoBean);
        }


        //圆圈的半径
        mRadius = AppUtils.dp2px(mContext, 4);
        //圆圈和下面直线之间的间隙
        mCircle_line_gap = AppUtils.dp2px(mContext, 2);
        //圆圈的个数
        mCircleSize = mStepViewTwoBeans.size();
        //直线的个数
        mLineSize = mCircleSize - 1;
        //直线的高度
        mLineHeight = AppUtils.dp2px(mContext, 64);
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
            //如果是wrap_content，我们要得到控件需要多大的尺寸
            //控件的高度就是View内容的高度加上上下的内边距。内边距就是padding值
            height =paddingTop + paddingBottom + mRadius * 2 * mCircleSize + mLineHeight * mLineSize + mCircle_line_gap * mLineSize * 2+200;
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
                "viewLeft=" + viewLeft + ", viewTop=" + viewTop + ", viewRight=" + viewRight + ", viewBottom=" + viewBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProcess(canvas);
    }

    private void drawProcess(Canvas canvas) {
        //圆圈和右边
        int circle_text_gap = AppUtils.dp2px(mContext, 10);
        //直线的宽度
        int lineWidth = AppUtils.dp2px(mContext, 2);
        //每个圆圈坐标点的集合
        int[][] circlesLocations = new int[mCircleSize][2];
        //每个直线矩形的坐标集合
        int[][] linesLocations = new int[mLineSize][4];
        for (int i = 0; i < mCircleSize; i++) {
            StepViewTwoBean stepViewTwoBean = mStepViewTwoBeans.get(i);
            //圆圈X坐标
            circlesLocations[i][0] = paddingLeft + mRadius;
            //圆圈Y坐标
            circlesLocations[i][1] = paddingTop + mRadius + i * (2 * mCircle_line_gap + 2 * mRadius + mLineHeight);
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
            int Xtext = mRadius * 2 + circle_text_gap + paddingLeft;
            int Ytext = paddingTop + stringHeight + i * (2 * mRadius + 2 * mCircle_line_gap + mLineHeight);
            canvas.drawText(location, Xtext, Ytext, mTextPaint);

            mTextPaint.setColor(stepViewTwoBean.highlight ? Color.parseColor("#35CBD1") : Color.parseColor("#9B9B9B"));
            mTextPaint.setTextSize(AppUtils.sp2px(mContext, 11));
            String time = stepViewTwoBean.time;
            int stringHeight1 = AppUtils.getStringHeight(time, mTextPaint);
            canvas.drawText(time, Xtext, Ytext + stringHeight1 + AppUtils.dp2px(mContext, 10), mTextPaint);
        }
    }

}
