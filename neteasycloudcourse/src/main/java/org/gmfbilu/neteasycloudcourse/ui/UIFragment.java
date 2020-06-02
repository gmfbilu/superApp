package org.gmfbilu.neteasycloudcourse.ui;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.MaterialDesignFragment;
import org.gmfbilu.neteasycloudcourse.ui.屏幕适配.ScreenShiPeiFragment;
import org.gmfbilu.neteasycloudcourse.ui.补充课程.SupplementCourseFragment;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class UIFragment extends BaseFragment {


    public static UIFragment newInstance() {
        Bundle args = new Bundle();
        UIFragment fragment = new UIFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_screenshipei).setOnClickListener(this);
        view.findViewById(R.id.bt_MaterialDesign).setOnClickListener(this);
        view.findViewById(R.id.bt_supplementcourse).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_supplementcourse) {
            start(SupplementCourseFragment.newInstance());
        } else if (id == R.id.bt_screenshipei) {
            start(ScreenShiPeiFragment.newInstance());
        }else if (id==R.id.bt_MaterialDesign){
            start(MaterialDesignFragment.newInstance());
        }
    }
}
