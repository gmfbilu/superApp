package org.gmfbilu.superapp.module_util.recyclerView.simple;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.module_util.R;
import org.gmfbilu.superapp.module_util.recyclerView.baseRecyclerView.adapter.BaseViewHolder;

public class SimpleViewHolder extends BaseViewHolder<SimpleBeen> {

    private TextView mTv_name;

    public SimpleViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_util_recyclerview_item_simple);
        mTv_name = $(R.id.tv_name);
    }

    @Override
    public void setData(SimpleBeen data) {
        super.setData(data);
        mTv_name.setText(data.name);
    }
}
