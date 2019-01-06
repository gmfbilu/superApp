package org.gmfbilu.superapp.module_util.recyclerView.multiType;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.module_util.R;

public class AdViewHolder extends BaseViewHolder<Ad> {

    private TextView tv_des;
    private ImageView iv_pic;

    public AdViewHolder(ViewGroup parent) {
        super(parent, R.layout.module_util_recyclerview_item_multi_type_ad);
        tv_des = $(R.id.tv_des);
        iv_pic = $(R.id.iv_pic);
    }

    @Override
    public void setData(Ad data) {
        tv_des.setText(data.url);
        iv_pic.setBackgroundResource(R.mipmap.lib_base_ic_launcher);
    }
}
