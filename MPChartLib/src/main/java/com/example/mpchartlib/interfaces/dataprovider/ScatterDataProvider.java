package com.example.mpchartlib.interfaces.dataprovider;


import com.example.mpchartlib.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
