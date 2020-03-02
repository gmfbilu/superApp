package com.example.mpchartlib.interfaces.dataprovider;


import com.example.mpchartlib.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
