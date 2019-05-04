package org.gmfbilu.superapp.module_view.recyclerView.easyRecyclerView.simple;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class SimpleViewHolder extends BaseViewHolder<SimpleBeen> {

    private TextView mTv_name;

    public SimpleViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_view_recyclerview_item_simple);
        mTv_name = $(R.id.tv_name);
    }

    @Override
    public void setData(SimpleBeen data) {
        super.setData(data);
        mTv_name.setText(data.name);
    }
}
