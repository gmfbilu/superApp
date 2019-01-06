package org.gmfbilu.superapp.module_googlelibrary.switch_checkbox_listpreference.search;

import android.content.Context;
import android.view.ViewGroup;

import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.RecyclerArrayAdapter;


public class SearchAdapter extends RecyclerArrayAdapter<String> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent);
    }
}
