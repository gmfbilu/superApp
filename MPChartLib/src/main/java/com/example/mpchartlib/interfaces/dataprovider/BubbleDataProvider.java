package com.example.mpchartlib.interfaces.dataprovider;


import com.example.mpchartlib.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
