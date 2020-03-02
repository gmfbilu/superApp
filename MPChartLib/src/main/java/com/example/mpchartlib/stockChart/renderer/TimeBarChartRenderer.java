package com.example.mpchartlib.stockChart.renderer;

import android.graphics.Canvas;

import com.example.mpchartlib.animation.ChartAnimator;
import com.example.mpchartlib.buffer.BarBuffer;
import com.example.mpchartlib.data.BarData;
import com.example.mpchartlib.data.BarEntry;
import com.example.mpchartlib.highlight.Highlight;
import com.example.mpchartlib.highlight.Range;
import com.example.mpchartlib.interfaces.dataprovider.BarDataProvider;
import com.example.mpchartlib.interfaces.datasets.IBarDataSet;
import com.example.mpchartlib.renderer.BarChartRenderer;
import com.example.mpchartlib.utils.ColorTemplate;
import com.example.mpchartlib.utils.Transformer;
import com.example.mpchartlib.utils.Utils;
import com.example.mpchartlib.utils.ViewPortHandler;


public class TimeBarChartRenderer extends BarChartRenderer {

    public TimeBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {
        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x;

            for (int i = 0, count = Math.min((int) (Math.ceil((float) (dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {

                BarEntry e = dataSet.getEntryForIndex(i);

                x = e.getX();

                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;

                trans.rectValueToPixel(mBarShadowRectBuffer);

                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right)) {
                    continue;
                }

                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left)) {
                    break;
                }

                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();

                c.drawRect(mBarShadowRectBuffer, mShadowPaint);
            }
        }

        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());

        buffer.feed(dataSet);

        trans.pointValuesToPixel(buffer.buffer);

        for (int j = 0; j < buffer.size(); j += 4) {

            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                continue;
            }

            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) {
                break;
            }


            // Set the color for the currently drawn value. If the index
            // is out of bounds, reuse colors.
            mRenderPaint.setColor(dataSet.getColor(j / 4));


            int i = j / 4;
//                if (i > 0) {
            Object openClose = dataSet.getEntryForIndex(i).getData();
            if (openClose == null) {
                if (i > 0) {
                    if (dataSet.getEntryForIndex(i).getY() > dataSet.getEntryForIndex(i - 1).getY()) {
                        mRenderPaint.setColor(dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE ?
                                dataSet.getColor(j) :
                                dataSet.getIncreasingColor());
                        mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());
                    } else {
                        mRenderPaint.setColor(dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE ?
                                dataSet.getColor(j) :
                                dataSet.getDecreasingColor());
                        mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());
                    }
                } else {
                    mRenderPaint.setColor(dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE ?
                            dataSet.getColor(j) :
                            dataSet.getIncreasingColor());
                    mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());
                }
            } else {//根据开平判断柱状图的颜色填充
                float value = (Float) openClose;
                if (value > 0) {//表示增加
                    mRenderPaint.setColor(dataSet.getIncreasingColor() == ColorTemplate.COLOR_NONE ?
                            dataSet.getColor(j) :
                            dataSet.getIncreasingColor());
                    mRenderPaint.setStyle(dataSet.getIncreasingPaintStyle());
                } else if (value <= 0) {
                    mRenderPaint.setColor(dataSet.getDecreasingColor() == ColorTemplate.COLOR_NONE ?
                            dataSet.getColor(j) :
                            dataSet.getDecreasingColor());
                    mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());
                }
//                else if (value == 0) {
//                    mRenderPaint.setColor(dataSet.getNeutralColor() == ColorTemplate.COLOR_NONE ?
//                            dataSet.getColor(j) :
//                            dataSet.getNeutralColor());
//                    mRenderPaint.setStyle(dataSet.getDecreasingPaintStyle());
//                }
            }


            c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                    buffer.buffer[j + 3], mRenderPaint);

            if (drawBorder) {
                c.drawRect(buffer.buffer[j], buffer.buffer[j + 1], buffer.buffer[j + 2],
                        buffer.buffer[j + 3], mBarBorderPaint);
            }
        }
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

        BarData barData = mChart.getBarData();

        for (Highlight high : indices) {

            IBarDataSet set = barData.getDataSetByIndex(high.getDataSetIndex());

            if (set == null || !set.isHighlightEnabled()) {
                continue;
            }

            BarEntry e = set.getEntryForXValue(high.getX(), high.getY());

            if (!isInBoundsX(e, set)) {
                continue;
            }

            Transformer trans = mChart.getTransformer(set.getAxisDependency());

            mHighlightPaint.setColor(set.getHighLightColor());
            mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(set.getHighlightLineWidth()));

            boolean isStack = (high.getStackIndex() >= 0 && e.isStacked()) ? true : false;

            final float y1;
            final float y2;

            if (isStack) {

                if (mChart.isHighlightFullBarEnabled()) {

                    y1 = e.getPositiveSum();
                    y2 = -e.getNegativeSum();

                } else {

                    Range range = e.getRanges()[high.getStackIndex()];

                    y1 = range.from;
                    y2 = range.to;
                }
            } else {
                y1 = e.getY();
                y2 = 0.f;
            }

            prepareBarHighlight(e.getX(), y1, y2, barData.getBarWidth() / 2f, trans);

            setHighlightDrawPos(high, mBarRect);

            c.drawLine(mBarRect.centerX(), mViewPortHandler.getContentRect().bottom, mBarRect.centerX(), 0, mHighlightPaint);
        }
    }
}
