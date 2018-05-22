package org.gmfbilu.superapp.module_googlelibrary.switch_checkbox_listpreference.search;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class SearchAdapter extends RecyclerArrayAdapter<String> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent);
    }
}
