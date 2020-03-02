package com.example.mpchartlib.interfaces.dataprovider;


import com.example.mpchartlib.components.YAxis;
import com.example.mpchartlib.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
