package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.section;


import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.List;

import androidx.annotation.NonNull;

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {

    /**
     * 第一个是item的，第二个是head的
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }

    /**
     * Head布局
     * 在convertHead方法里面加载head数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convertHead(BaseViewHolder helper, final SectionBean item) {
        helper.setText(R.id.tv_name, item.header);
        helper.addOnClickListener(R.id.tv_name);
    }


    /**
     * item布局
     * 在convert方法里面加载item数据
     *
     * @param helper
     * @param item
     */
    @Override
    protected void convert(@NonNull BaseViewHolder helper, SectionBean item) {
        Video video = (Video) item.t;
        ImageView img = helper.getView(R.id.img);
        Glide.with(mContext).load(video.img).into(img);
    }
}
