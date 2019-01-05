package org.gmfbilu.superapp.module_util.search;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_util.R;

public class SearchHotViewHolder extends BaseViewHolder<String> {

    private TextView tv_hot;

    public SearchHotViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_util_recyclerview_item_search_hot);
        tv_hot = $(R.id.tv_hot);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv_hot.setText(data);
    }
}
