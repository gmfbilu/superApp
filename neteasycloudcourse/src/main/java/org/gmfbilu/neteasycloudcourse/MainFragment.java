package org.gmfbilu.neteasycloudcourse;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;

/**
 * 主界面Fragment,根Fragment
 */


public class MainFragment extends BaseFragment {



    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_classBeforePrepare01).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_classBeforePrepare01) {

        }
    }

}
