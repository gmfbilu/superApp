package org.gmfbilu.superapp.module_view.kLine.MPChart.land;

import android.os.Bundle;
import android.view.View;

import com.example.mpchartlib.stockChart.KLineChart;
import com.example.mpchartlib.stockChart.dataManage.KLineDataManage;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.MPChart.ChartData;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import me.jessyan.autosize.internal.CancelAdapt;

public class LandKlineFragment extends BaseFragment implements CancelAdapt {

    private int mType;//日K：1；周K：2；月K：3

    private KLineChart combinedchart;
    private KLineDataManage kLineData;
    private JSONObject object;

    public static LandKlineFragment newInstance(int type){
        LandKlineFragment fragment = new LandKlineFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        combinedchart = view.findViewById(R.id.chart);
        initChart();
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_land_kline;
    }

    @Override
    public void onClick(View v) {

    }

    private void initChart() {
        kLineData = new KLineDataManage(getActivity());
        combinedchart.initChart(true);
        try {
            if(mType == 1){
                object = new JSONObject(ChartData.KLINEDATA);
            }else if(mType == 2){
                object = new JSONObject(ChartData.KLINEWEEKDATA);
            }else if(mType == 3){
                object = new JSONObject(ChartData.KLINEMONTHDATA);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //上证指数代码000001.IDX.SH
        kLineData.parseKlineData(object,"000001.IDX.SH",true);
        combinedchart.setDataToChart(kLineData);
    }

}
