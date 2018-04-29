package org.gmfbilu.superapp.module_java;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_java.retrofit_rxjava.RxJava_RetrofitFragment;
import org.gmfbilu.superapp.module_java.rxJava.RxJavaFragment;

/**
 * Created by gmfbilu on 18-3-11.
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
        view.findViewById(R.id.module_java_bt_rxjava).setOnClickListener(this);
        view.findViewById(R.id.module_java_bt_rxjava_retrofit).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_java_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_java_bt_rxjava) {
            start(RxJavaFragment.newInstance());
        } else if (id == R.id.module_java_bt_rxjava_retrofit) {
            start(RxJava_RetrofitFragment.newInstance());
        }
    }
}
