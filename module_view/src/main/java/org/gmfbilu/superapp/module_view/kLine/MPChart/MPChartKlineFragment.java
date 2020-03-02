package org.gmfbilu.superapp.module_view.kLine.MPChart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mpchartlib.stockChart.OneDayChart;
import com.example.mpchartlib.stockChart.dataManage.TimeDataManage;
import com.google.android.material.tabs.TabLayout;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.MPChart.land.MPChartLandActivity;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class MPChartKlineFragment extends BaseFragment {


    private TabLayout tab;
    private ViewPager viewpager;

    private OneDayChart chart;
    private JSONObject object;
    private TimeDataManage kTimeData = new TimeDataManage();


    public static MPChartKlineFragment newInstance() {
        Bundle args = new Bundle();
        MPChartKlineFragment fragment = new MPChartKlineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        chart = view.findViewById(R.id.chart);
        initChart();

        tab = view.findViewById(R.id.tab);
        viewpager = view.findViewById(R.id.viewpager);

        Fragment[] fragments = {MPChartKlineWuDangFragment.newInstance(), MPChartKlineWuDangDetailFragment.newInstance()};
        String[] titles = {"五档", "明细"};
        viewpager.setOffscreenPageLimit(fragments.length);
        viewpager.setAdapter(new SimpleFragmentPagerAdapter(_mActivity.getSupportFragmentManager(), fragments, titles));
        tab.setupWithViewPager(viewpager);

        view.findViewById(R.id.bt_land).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_kline_mpchart;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_land) {
            startActivity(new Intent(_mActivity, MPChartLandActivity.class));
        }
    }

    private void initChart() {
        //初始化，是否横屏
        chart.initChart(false);
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
