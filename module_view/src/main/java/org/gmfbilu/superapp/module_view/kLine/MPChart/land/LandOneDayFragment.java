package org.gmfbilu.superapp.module_view.kLine.MPChart.land;

import android.os.Bundle;
import android.view.View;

import com.example.mpchartlib.stockChart.OneDayChart;
import com.example.mpchartlib.stockChart.dataManage.TimeDataManage;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.MPChart.ChartData;
import org.json.JSONException;
import org.json.JSONObject;

import me.jessyan.autosize.internal.CancelAdapt;

public class LandOneDayFragment extends BaseFragment implements CancelAdapt {

    private OneDayChart chart;
    private JSONObject object;
    private TimeDataManage kTimeData = new TimeDataManage();

    public static LandOneDayFragment newInstance() {
        Bundle args = new Bundle();
        LandOneDayFragment fragment = new LandOneDayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        chart = view.findViewById(R.id.chart);
        initChart();
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_land_oneday;
    }

    @Override
    public void onClick(View v) {

    }

    private void initChart() {
        //初始化，是否横屏
        chart.initChart(true);
        //测试数据
        try {
            object = new JSONObject(ChartData.TIMEDATA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //上证指数代码000001.IDX.SH
        kTimeData.parseTimeData(object, "000001.IDX.SH", 0);
        chart.setDataToChart(kTimeData);
    }
}
