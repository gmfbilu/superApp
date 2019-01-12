package org.gmfbilu.superapp.module_view.customViews.ratingBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;


public class RatingBar extends BaseView {

    int width;
    int gap;
    int round;
    int pentagramR;
    int starOffsetX;
    private Paint mPaint;
    private Path mPath;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        width = AppUtils.dp2px(12);
        gap = AppUtils.dp2px(2);
        round = AppUtils.dp2px(3);
        //五角星外圆半径
        pentagramR = AppUtils.dp2px(4);
        starOffsetX = AppUtils.dp2px(1);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(AppUtils.dp2px(72), AppUtils.dp2px(12));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rectSize = 5;
        int starPoint = 7;//10分满分
        int halfStar = starPoint % (10 / rectSize);
        int startSize = (int) Math.floor(starPoint / (10 / rectSize));
        mPaint.setColor(Color.parseColor("#cccccc"));
        for (int i = 0; i < rectSize; i++) {
            canvas.drawRoundRect((width + gap) * i, 0, (1 + i) * width + i * gap, width, round, round, mPaint);
        }
        mPaint.setColor(Color.parseColor("#FFB400"));
        for (int i = 0; i < startSize; i++) {
            canvas.drawRoundRect((width + gap) * i, 0, (1 + i) * width + i * gap, width, round, round, mPaint);
        }
        if (halfStar != 0) {
            int[] bigLocation = {(width + gap) * startSize, 0, (width + gap) * startSize + width, width};
            int[] topSmallRound = {bigLocation[0], bigLocation[1], bigLocation[0] + 2 * round, 2 * round};
            mPath.setLastPoint(bigLocation[0] + round, round);
            mPath.arcTo(topSmallRound[0], topSmallRound[1], topSmallRound[2], topSmallRound[3], 180, 90, false);
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            int[] bottomSmallRound = {bigLocation[0], round * 2, topSmallRound[2], width};
            mPath.setLastPoint(bigLocation[0] + round, width - round);
            mPath.arcTo(bottomSmallRound[0], bottomSmallRound[1], bottomSmallRound[2], bottomSmallRound[3], 90, 90, false);
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            mPath.setLastPoint(topSmallRound[0], round);
            mPath.lineTo(topSmallRound[0], width - round);
            mPath.lineTo(topSmallRound[0] + round, width - round);
            mPath.lineTo(bigLocation[0] + round, round);
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.drawRect(bigLocation[0] + round, 0, bigLocation[0] + width / 2, width, mPaint);
        }
        mPaint.setColor(Color.parseColor("#ffffff"));
        for (int i = 0; i < rectSize; i++) {
            canvas.save();
            canvas.translate(i * (width + gap) + width / 2, width / 2);
            mPath.setLastPoint(0, -pentagramR);
            mPath.lineTo(starOffsetX, 0);
            mPath.lineTo(-starOffsetX, 0);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.rotate(72);
            mPath.setLastPoint(0, -pentagramR);
            mPath.lineTo(starOffsetX, 0);
            mPath.lineTo(-starOffsetX, 0);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.rotate(72);
            mPath.setLastPoint(0, -pentagramR);
            mPath.lineTo(starOffsetX, 0);
            mPath.lineTo(-starOffsetX, 0);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.rotate(72);
            mPath.setLastPoint(0, -pentagramR);
            mPath.lineTo(starOffsetX, 0);
            mPath.lineTo(-starOffsetX, 0);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.rotate(72);
            mPath.setLastPoint(0, -pentagramR);
            mPath.lineTo(starOffsetX, 0);
            mPath.lineTo(-starOffsetX, 0);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
            mPath.reset();
            canvas.restore();
        }
    }

}
