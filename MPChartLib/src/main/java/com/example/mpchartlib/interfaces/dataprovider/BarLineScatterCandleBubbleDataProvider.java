package com.example.mpchartlib.interfaces.dataprovider;


import com.example.mpchartlib.components.YAxis;
import com.example.mpchartlib.data.BarLineScatterCandleBubbleData;
import com.example.mpchartlib.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(YAxis.AxisDependency axis);

    boolean isInverted(YAxis.AxisDependency axis);

    float getLowestVisibleX();

    float getHighestVisibleX();

    @Override
    BarLineScatterCandleBubbleData getData();
}
