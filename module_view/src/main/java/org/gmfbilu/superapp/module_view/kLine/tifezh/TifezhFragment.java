package org.gmfbilu.superapp.module_view.kLine.tifezh;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;

/**
 * https://github.com/tifezh/KChartView
 */
public class TifezhFragment extends BaseFragment {


    public static TifezhFragment newInstance() {
        Bundle args = new Bundle();
        TifezhFragment fragment = new TifezhFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {

    }

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
