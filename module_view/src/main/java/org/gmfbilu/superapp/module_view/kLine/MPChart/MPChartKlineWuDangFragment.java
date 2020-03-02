package org.gmfbilu.superapp.module_view.kLine.MPChart;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.MathUtils;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class MPChartKlineWuDangFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private WuDangAdapter mWuDangAdapter;

    public static MPChartKlineWuDangFragment newInstance() {
        Bundle args = new Bundle();
        MPChartKlineWuDangFragment fragment = new MPChartKlineWuDangFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mWuDangAdapter = new WuDangAdapter(R.layout.module_view_recyclerview_item_wudang, R.layout.module_view_recyclerview_item_wudang_section, null);
        recyclerView.setAdapter(mWuDangAdapter);

        View headerOne = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_wudang, null);
        mWuDangAdapter.addHeaderView(headerOne, 0);
        TextView tv_one = headerOne.findViewById(R.id.tv_one);

        View footOne = _mActivity.getLayoutInflater().inflate(R.layout.module_view_recyclerview_header_wudang, null);
        mWuDangAdapter.addFooterView(footOne, 0);

        mWuDangAdapter.addData(getData());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_kline_mpchart_wudang;
    }

    @Override
    public void onClick(View v) {

    }

    private ArrayList<WuDangBean> getData() {
        ArrayList<WuDangBean> wuDangBeans = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            WuDangBean wuDangBean;
            if (i == 5) {
                wuDangBean = new WuDangBean(true, "");
            } else {
                WuDang wuDang = new WuDang();
                wuDang.one = "卖" + i;
                wuDang.two = 1169.02 + i + "";
                wuDang.three = MathUtils.getRandom(0, 100) + "";
                wuDangBean = new WuDangBean(wuDang);
            }
            wuDangBeans.add(wuDangBean);
        }
        return wuDangBeans;
    }


}
