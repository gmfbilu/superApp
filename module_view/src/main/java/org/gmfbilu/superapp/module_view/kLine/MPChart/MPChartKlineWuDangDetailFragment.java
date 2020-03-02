package org.gmfbilu.superapp.module_view.kLine.MPChart;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.MathUtils;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class MPChartKlineWuDangDetailFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private WuDangDetailAdapter mWuDangDetailAdapter;

    public static MPChartKlineWuDangDetailFragment newInstance() {
        Bundle args = new Bundle();
        MPChartKlineWuDangDetailFragment fragment = new MPChartKlineWuDangDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mWuDangDetailAdapter = new WuDangDetailAdapter(R.layout.module_view_recyclerview_item_wudang_detail, null);
        recyclerView.setAdapter(mWuDangDetailAdapter);
        mWuDangDetailAdapter.addData(getData());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_kline_mpchart_wudang_detail;
    }

    @Override
    public void onClick(View v) {

    }

    private ArrayList<WuDangDetail> getData() {
        ArrayList<WuDangDetail> wuDangDetails = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            WuDangDetail wuDangDetail = new WuDangDetail();
            wuDangDetail.time = "14:26";
            wuDangDetail.index = (1150.28 + i) + "";
            wuDangDetail.num = MathUtils.getRandom(0, 100) + "";
            wuDangDetails.add(wuDangDetail);
        }
        return wuDangDetails;
    }


}
