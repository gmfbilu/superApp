package org.gmfbilu.superapp.module_view.search.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class LocalAllBigViewHolder extends BaseViewHolder<String> {


    private TextView tv;

    public LocalAllBigViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_view_recyclerview_item_search_local_all_big);
        tv = $(R.id.tv);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv.setText(data);
    }
}
