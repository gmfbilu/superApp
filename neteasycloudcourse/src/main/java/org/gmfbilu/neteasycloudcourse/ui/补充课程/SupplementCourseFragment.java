package org.gmfbilu.neteasycloudcourse.ui.补充课程;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview.RecyclerViewFragment;
import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class SupplementCourseFragment extends BaseFragment {


    public static SupplementCourseFragment newInstance() {
        Bundle args = new Bundle();
        SupplementCourseFragment fragment = new SupplementCourseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_recyclerview).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui_supplementcourse;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id==R.id.bt_recyclerview){
            start(RecyclerViewFragment.newInstance());
        }
    }
}
