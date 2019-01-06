package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.search;

import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;


public class SearchViewHolder extends BaseViewHolder<String> {

    private TextView tv;

    public SearchViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_view_item_search);
        tv = $(R.id.tv);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv.setText(data);
    }
}
