package org.gmfbilu.superapp.module_view.search.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class LocalViewHolder extends BaseViewHolder<String> {


    private ImageView iv_shop_img;

    public LocalViewHolder(ViewGroup parent) {
        super(parent,R.layout.module_view_recyclerview_item_search_local);
        iv_shop_img = $(R.id.iv_shop_img);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }
}
