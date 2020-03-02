package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.multipleItem;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.List;

public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MultipleItemQuickAdapter(List<MultipleItem> data) {
        super(data);
        addItemType(MultipleItem.IMG, R.layout.module_view_recyclerview_item_multi_type_ad);
        addItemType(MultipleItem.TEXT, R.layout.module_view_recyclerview_item_multi_type_person);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        switch (helper.getItemViewType()) {
            case MultipleItem.IMG:
                TextView tv_des = helper.getView(R.id.tv_des);
                ImageView iv_pic = helper.getView(R.id.iv_pic);
                tv_des.setText(item.picDes);
                Glide.with(mContext).load(item.picUrl).into(iv_pic);
                break;
            case MultipleItem.TEXT:
                TextView tv_name = helper.getView(R.id.tv_name);
                TextView tv_age = helper.getView(R.id.tv_age);
                tv_name.setText(item.name);
                tv_age.setText(item.age);
                break;
        }
    }
}
