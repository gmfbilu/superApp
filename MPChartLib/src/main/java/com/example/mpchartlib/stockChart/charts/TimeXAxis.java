package com.example.mpchartlib.stockChart.charts;

import android.util.SparseArray;

import com.example.mpchartlib.components.XAxis;


public class TimeXAxis extends XAxis {
    private SparseArray<String> labels;

    public SparseArray<String> getXLabels() {
        return labels;
    }

    public void setXLabels(SparseArray<String> labels) {
        this.labels = labels;
    }
}
