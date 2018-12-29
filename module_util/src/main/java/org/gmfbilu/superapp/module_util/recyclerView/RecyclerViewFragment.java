package org.gmfbilu.superapp.module_util.recyclerView;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;
import org.gmfbilu.superapp.module_util.recyclerView.headerFooter.HeaderFooterRecyclerViewFragment;
import org.gmfbilu.superapp.module_util.recyclerView.multiType.MultiTypeRecyclerViewFragment;
import org.gmfbilu.superapp.module_util.recyclerView.simple.SimpleRecyclerViewFragment;

public class RecyclerViewFragment extends BaseFragment {


    public static RecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_util_bt_easy).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_header_footer).setOnClickListener(this);
        view.findViewById(R.id.module_util_bt_multitype).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_recyclerview;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_util_bt_easy) {
            start(SimpleRecyclerViewFragment.newInstance());
        }else if (id==R.id.module_util_bt_header_footer){
            start(HeaderFooterRecyclerViewFragment.newInstance());
        }else if (id==R.id.module_util_bt_multitype){
            start(MultiTypeRecyclerViewFragment.newInstance());
        }
    }
}
