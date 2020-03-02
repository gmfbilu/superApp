package org.gmfbilu.superapp.module_view.kLine;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.MPChart.MPChartKlineFragment;
import org.gmfbilu.superapp.module_view.kLine.tifezh.TifezhFragment;

public class KLineFragment extends BaseFragment {


    public static KLineFragment newInstance() {
        Bundle args = new Bundle();
        KLineFragment fragment = new KLineFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_tifezh).setOnClickListener(this);
        view.findViewById(R.id.bt_MPChart).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_kline;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_tifezh) {
            start(TifezhFragment.newInstance());
        } else if (id == R.id.bt_MPChart) {
            start(MPChartKlineFragment.newInstance());
        }
    }
}
