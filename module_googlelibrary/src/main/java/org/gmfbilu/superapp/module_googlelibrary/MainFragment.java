package org.gmfbilu.superapp.module_googlelibrary;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;

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
        view.findViewById(R.id.googlelibrary_bt_constraintlayout).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.goolelibrary_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.googlelibrary_bt_constraintlayout){
            start(ConstraintLayoutFragment.newInstance());
        }
    }
}
