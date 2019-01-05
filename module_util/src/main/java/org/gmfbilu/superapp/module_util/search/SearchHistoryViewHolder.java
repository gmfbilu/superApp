package org.gmfbilu.superapp.module_util.search;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_util.R;

public class SearchHistoryViewHolder extends BaseViewHolder<String> {

    private TextView tv_history;

    public SearchHistoryViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_util_recyclerview_item_search_history);
        tv_history = $(R.id.tv_history);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv_history.setText(data);
    }
}
