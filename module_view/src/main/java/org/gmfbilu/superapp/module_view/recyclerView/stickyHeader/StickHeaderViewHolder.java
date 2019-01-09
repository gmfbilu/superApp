package org.gmfbilu.superapp.module_view.recyclerView.stickyHeader;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class StickHeaderViewHolder extends BaseViewHolder<String> {

    private TextView tv_name;

    public StickHeaderViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_view_recyclerview_item_skict_header);
        tv_name = $(R.id.tv_name);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv_name.setText(data);
    }
}
