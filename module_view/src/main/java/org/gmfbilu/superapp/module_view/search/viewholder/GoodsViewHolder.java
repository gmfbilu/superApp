package org.gmfbilu.superapp.module_view.search.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_view.R;

public class GoodsViewHolder extends BaseViewHolder<String> {


    private ImageView iv_pic;

    public GoodsViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_view_recyclerview_item_search_goods);
        iv_pic = $(R.id.iv_pic);
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }
}
