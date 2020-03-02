package com.example.mpchartlib.stockChart.markerView;

import android.content.Context;
import android.widget.TextView;

import com.example.mpchartlib.R;
import com.example.mpchartlib.components.MarkerView;
import com.example.mpchartlib.data.Entry;
import com.example.mpchartlib.highlight.Highlight;
import com.example.mpchartlib.utils.MPPointF;
import com.example.mpchartlib.utils.NumberUtils;


public class LeftMarkerView extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView markerTv;
    private float num;
    private int precision;

    public LeftMarkerView(Context context, int layoutResource, int precision) {
        super(context, layoutResource);
        this.precision = precision;
        markerTv = (TextView) findViewById(R.id.marker_tv);
        markerTv.setTextSize(10);
    }

    public void setData(float num) {
        this.num = num;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        markerTv.setText(NumberUtils.keepPrecisionR(num, precision));
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
