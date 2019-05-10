package org.gmfbilu.superapp.module_view.customViews.paintView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

import androidx.annotation.Nullable;

public class PaintView extends BaseView {


    private TextPaint mTextPaint;
    private Paint mPaint;
    private int mTextHeight = AppUtils.dp2px(15);

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTextPaint = new TextPaint();
        initTextPaint();

        mPaint = new Paint();
        //通过设置Flag来应用抗锯齿效果
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //通过Paint的API来设置抗锯齿效果
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    private void initTextPaint() {
        //通过设置Flag来应用抗锯齿效果
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //通过Paint的API来设置抗锯齿效果
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(AppUtils.sp2px(16));
        mTextPaint.setColor(Color.BLACK);
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
            height = AppUtils.dp2px(200);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("默认对齐", _mViewWidth / 2, mTextHeight, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        //设置字体加粗
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("左对齐", _mViewWidth / 2, mTextHeight * 2, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //设置加粗、倾斜
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC);
        mTextPaint.setTypeface(font);
        canvas.drawText("中央对齐", _mViewWidth / 2, mTextHeight * 3, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        //从assets资源中加载,加载自定义字体的API
        //Typeface createFromAsset(AssetManager mgr, String path)
        //从文件中加载字体
        //Typeface createFromFile(String path)
        canvas.drawText("右对齐", _mViewWidth / 2, mTextHeight * 4, mTextPaint);

        //重置所有属性
        mTextPaint.reset();
        initTextPaint();
        //设置下划线
        mTextPaint.setUnderlineText(true);
        //设置文字中间的删除线
        mTextPaint.setStrikeThruText(true);
        canvas.drawText("下划线和中间删除线", _mViewWidth / 2, mTextHeight * 5, mTextPaint);
        mTextPaint.reset();
        initTextPaint();
        //得到文字的宽度
        float textWidth = mTextPaint.measureText("水平方向居中问题");
        canvas.drawText("水平方向居中问题", _mViewWidth / 2 - textWidth / 2, mTextHeight * 6, mTextPaint);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        //文字的高度
        float textHeight = Math.abs(fontMetrics.ascent) - fontMetrics.descent;
        canvas.drawText("竖直方向居中问题", (_mViewWidth - textWidth) / 2, mTextHeight * 7 + textHeight / 2, mTextPaint);

        //CornerPathEffect用于增加点与点之间的圆弧效果，CornerPathEffect中的参数表示圆弧效果的半径
        mPaint.setPathEffect(new CornerPathEffect(5));
        Path mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(_mViewWidth / 2, _mViewHeight / 2);
        canvas.drawPath(mPath, mPaint);
    }
}
