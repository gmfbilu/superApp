package org.gmfbilu.superapp.module_view.customViews.canvasView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.customViews.baseView.BaseView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LeafLoading extends BaseView {

    private Paint bgPaint, progressPaint;
    private int radius;
    private int rectLength, rectHeight;
    private int bgSmallCircleWidth;
    private int progressX, progressAngle;
    private int gap;
    private int smallRadius;
    private ValueAnimator mValueAnimator;

    private Bitmap mLeafBitmap;
    private int mLeafWidth, mLeafHeight;
    // 中等振幅大小
    private static final int MIDDLE_AMPLITUDE = 13;
    // 不同类型之间的振幅差距
    private static final int AMPLITUDE_DISPARITY = 5;

    // 总进度
    private static final int TOTAL_PROGRESS = 100;
    // 叶子飘动一个周期所花的时间
    private static final long LEAF_FLOAT_TIME = 3000;
    // 叶子旋转一周需要的时间
    private static final long LEAF_ROTATE_TIME = 2000;

    // 中等振幅大小
    private int mMiddleAmplitude = MIDDLE_AMPLITUDE;
    // 振幅差
    private int mAmplitudeDisparity = AMPLITUDE_DISPARITY;
    // 叶子飘动一个周期所花的时间
    private long mLeafFloatTime = LEAF_FLOAT_TIME;
    // 叶子旋转一周需要的时间
    private long mLeafRotateTime = LEAF_ROTATE_TIME;
    // 用于产生叶子信息
    private LeafFactory mLeafFactory;
    // 产生出的叶子信息
    private List<Leaf> mLeafInfos;
    // 用于控制随机增加的时间不抱团
    private int mAddTime;


    public LeafLoading(Context context) {
        this(context, null);
    }

    public LeafLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeafLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        radius = AppUtils.dp2px(25);
        rectLength = AppUtils.dp2px(150);
        rectHeight = AppUtils.dp2px(50);
        bgSmallCircleWidth = AppUtils.dp2px(2);
        gap = AppUtils.dp2px(5);
        smallRadius = radius - gap;

        bgPaint = new Paint();
        bgPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        progressPaint = new Paint();
        progressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.parseColor("#ffa700"));
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mLeafBitmap = ((BitmapDrawable) _mContext.getResources().getDrawable(R.mipmap.leaf)).getBitmap();
        mLeafWidth = mLeafBitmap.getWidth();
        mLeafHeight = mLeafBitmap.getHeight();
        mLeafFloatTime = LEAF_FLOAT_TIME;
        mLeafRotateTime = LEAF_ROTATE_TIME;
        mLeafFactory = new LeafFactory();
        mLeafInfos = mLeafFactory.generateLeafs();
        startLoading();
    }

    private void startLoading() {
        progressX = 0;
        progressAngle = 0;
        mValueAnimator = ValueAnimator.ofFloat(0, 1f);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                progressX = (int) (currentValue * (radius + rectLength - gap));
                progressAngle = (int) (currentValue * 360);
                if (currentValue == 1) {
                    startLoading();
                }
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.setDuration(5000);
        mValueAnimator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(AppUtils.getScreenWidth(), AppUtils.dp2px(50));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景
        bgPaint.setColor(Color.parseColor("#fce399"));
        canvas.drawArc(0, 0, radius * 2, radius * 2, 90, 180, true, bgPaint);
        canvas.drawArc(rectLength, 0, rectLength + radius * 2, radius * 2, 270, 180, true, bgPaint);
        canvas.drawRect(radius, 0, radius + rectLength, radius * 2, bgPaint);

        //弧形进度
        Path path1 = new Path();
        path1.addRect(gap, gap, gap + progressX, rectHeight - gap, Path.Direction.CCW);
        Path path2 = new Path();
        path2.addArc(gap, gap, gap + 2 * smallRadius, gap + 2 * smallRadius, 90, 180);
        path1.op(path2, Path.Op.INTERSECT);
        canvas.drawPath(path1, progressPaint);
        if (progressX - gap > smallRadius) {
            //矩形进度
            canvas.drawRect(gap + smallRadius, gap, progressX, gap + 2 * smallRadius, progressPaint);
        }


        //旋转的叶片背景
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(radius + rectLength, radius, radius, bgPaint);
        bgPaint.setColor(Color.parseColor("#fdcb4a"));
        bgPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius + rectLength, radius, radius - bgSmallCircleWidth, bgPaint);


        //旋转的叶片
        canvas.save();
        bgPaint.setColor(Color.WHITE);
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.translate(radius + rectLength, radius);
        canvas.drawCircle(0, 0, AppUtils.dp2px(1), bgPaint);
        canvas.rotate(progressAngle * 5);
        for (int i = 0; i < 4; i++) {
            canvas.rotate(i * 90);
            canvas.drawArc(-AppUtils.dp2px(8), -(radius / 2 + AppUtils.dp2px(8)), AppUtils.dp2px(8), -(radius / 2 - AppUtils.dp2px(8)), 180, 180, true, bgPaint);
            Path path = new Path();
            path.setLastPoint(0, 0);
            path.lineTo(-AppUtils.dp2px(8), -radius / 2);
            path.lineTo(AppUtils.dp2px(8), -radius / 2);
            path.close();
            canvas.drawPath(path, bgPaint);
        }
        canvas.restore();
    }

    private enum StartType {
        LITTLE, MIDDLE, BIG
    }

    /**
     * 叶子对象，用来记录叶子主要数据
     *
     * @author Ajian_Studio
     */
    private class Leaf {

        // 在绘制部分的位置
        float x, y;
        // 控制叶子飘动的幅度
        StartType type;
        // 旋转角度
        int rotateAngle;
        // 旋转方向--0代表顺时针，1代表逆时针
        int rotateDirection;
        // 起始时间(ms)
        long startTime;
    }

    private class LeafFactory {
        private static final int MAX_LEAFS = 8;
        Random random = new Random();

        // 生成一个叶子信息
        public Leaf generateLeaf() {
            Leaf leaf = new Leaf();
            int randomType = random.nextInt(3);
            // 随时类型－ 随机振幅
            StartType type = StartType.MIDDLE;
            switch (randomType) {
                case 0:
                    break;
                case 1:
                    type = StartType.LITTLE;
                    break;
                case 2:
                    type = StartType.BIG;
                    break;
                default:
                    break;
            }
            leaf.type = type;
            // 随机起始的旋转角度
            leaf.rotateAngle = random.nextInt(360);
            // 随机旋转方向（顺时针或逆时针）
            leaf.rotateDirection = random.nextInt(2);
            // 为了产生交错的感觉，让开始的时间有一定的随机性
            mLeafFloatTime = mLeafFloatTime <= 0 ? LEAF_FLOAT_TIME : mLeafFloatTime;
            mAddTime += random.nextInt((int) (mLeafFloatTime * 2));
            leaf.startTime = System.currentTimeMillis() + mAddTime;
            return leaf;
        }

        // 根据最大叶子数产生叶子信息
        public List<Leaf> generateLeafs() {
            return generateLeafs(MAX_LEAFS);
        }

        // 根据传入的叶子数量产生叶子信息
        public List<Leaf> generateLeafs(int leafSize) {
            List<Leaf> leafs = new LinkedList<Leaf>();
            for (int i = 0; i < leafSize; i++) {
                leafs.add(generateLeaf());
            }
            return leafs;
        }
    }


    public void resume() {
        mValueAnimator.start();
    }

    public void pause() {
        mValueAnimator.pause();
    }

    public void destroy() {
        mValueAnimator.cancel();
    }

}
