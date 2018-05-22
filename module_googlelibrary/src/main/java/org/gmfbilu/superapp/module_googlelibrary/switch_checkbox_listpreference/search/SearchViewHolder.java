package org.gmfbilu.superapp.module_googlelibrary.switch_checkbox_listpreference.search;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import org.gmfbilu.superapp.module_googlelibrary.R;


public class SearchViewHolder extends BaseViewHolder<String> {

    private TextView tv;

    public SearchViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_googlelibrary_item_search);
        tv = $(R.id.tv);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
        tv.setText(data);
    }
}
