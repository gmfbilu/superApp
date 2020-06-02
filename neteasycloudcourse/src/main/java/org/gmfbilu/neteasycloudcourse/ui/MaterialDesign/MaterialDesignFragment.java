package org.gmfbilu.neteasycloudcourse.ui.MaterialDesign;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.neteasycloudcourse.R;
import org.gmfbilu.neteasycloudcourse.ui.MaterialDesign.自定义RecyclerView.ZiDingYiRecyclerViewFragment;
import org.gmfbilu.superapp.lib_base.base.BaseFragment;

public class MaterialDesignFragment extends BaseFragment {


    public static MaterialDesignFragment newInstance() {
        Bundle args = new Bundle();
        MaterialDesignFragment fragment = new MaterialDesignFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_zidingyirecyclerview).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_ui_materialdesign;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_zidingyirecyclerview) {
            start(new ZiDingYiRecyclerViewFragment());
        }
    }
}
