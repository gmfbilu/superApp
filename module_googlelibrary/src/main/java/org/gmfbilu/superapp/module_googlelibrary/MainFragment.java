package org.gmfbilu.superapp.module_googlelibrary;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_googlelibrary.constraintlayout.ConstraintLayoutFragment;
import org.gmfbilu.superapp.module_googlelibrary.switch_checkbox_listpreference.Switch_CheckBox_ListPreferenceFragment;


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
        view.findViewById(R.id.module_googlelibrary_bt_constraintlayout).setOnClickListener(this);
        view.findViewById(R.id.module_googlelibrary_bt_switch_checkbox_listpreference).setOnClickListener(this);
        view.findViewById(R.id.module_googlelibrary_bt_dialogfragment).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_googlelibrary_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_googlelibrary_bt_constraintlayout) {
            start(ConstraintLayoutFragment.newInstance());
        } else if (id == R.id.module_googlelibrary_bt_switch_checkbox_listpreference) {
            start(Switch_CheckBox_ListPreferenceFragment.newInstance());
        }else if (id==R.id.module_googlelibrary_bt_dialogfragment){
            start(DialogFragment.newInstance());
        }
    }
}
