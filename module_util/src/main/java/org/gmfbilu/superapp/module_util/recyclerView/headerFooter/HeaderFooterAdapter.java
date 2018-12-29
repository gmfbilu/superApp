package org.gmfbilu.superapp.module_util.recyclerView.headerFooter;

import android.content.Context;
import android.view.ViewGroup;

import org.gmfbilu.superapp.module_util.recyclerView.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_util.recyclerView.baseRecyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.module_util.recyclerView.simple.SimpleBeen;

public class HeaderFooterAdapter  extends RecyclerArrayAdapter<SimpleBeen> {


    public HeaderFooterAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HeaderFooterViewHolder(parent);
    }
}
