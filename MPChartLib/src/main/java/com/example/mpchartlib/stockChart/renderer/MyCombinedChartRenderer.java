package com.example.mpchartlib.stockChart.renderer;


import com.example.mpchartlib.animation.ChartAnimator;
import com.example.mpchartlib.charts.CombinedChart;
import com.example.mpchartlib.renderer.BubbleChartRenderer;
import com.example.mpchartlib.renderer.CombinedChartRenderer;
import com.example.mpchartlib.renderer.LineChartRenderer;
import com.example.mpchartlib.renderer.ScatterChartRenderer;
import com.example.mpchartlib.utils.ViewPortHandler;

public class MyCombinedChartRenderer extends CombinedChartRenderer {

    public MyCombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void createRenderers() {

        mRenderers.clear();

        CombinedChart chart = (CombinedChart) mChart.get();
        if (chart == null) {
            return;
        }

        CombinedChart.DrawOrder[] orders = chart.getDrawOrder();

        for (CombinedChart.DrawOrder order : orders) {

            switch (order) {
                case BAR:
                    if (chart.getBarData() != null) {
                        mRenderers.add(new TimeBarChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case BUBBLE:
                    if (chart.getBubbleData() != null) {
                        mRenderers.add(new BubbleChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case LINE:
                    if (chart.getLineData() != null) {
                        mRenderers.add(new LineChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case CANDLE:
                    if (chart.getCandleData() != null) {
                        mRenderers.add(new MyCandleStickChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                case SCATTER:
                    if (chart.getScatterData() != null) {
                        mRenderers.add(new ScatterChartRenderer(chart, mAnimator, mViewPortHandler));
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
