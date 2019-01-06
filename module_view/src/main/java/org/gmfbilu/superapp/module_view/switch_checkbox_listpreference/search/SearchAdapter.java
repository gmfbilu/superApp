package org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.search;

import android.content.Context;
import android.view.ViewGroup;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;


public class SearchAdapter extends RecyclerArrayAdapter<String> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchViewHolder(parent);
    }
}
