package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.headerfooter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class HeaderFooterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HeaderFooterAdapter(int layoutResId, ArrayList<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tweetName, item);
        //mContext
        //数据状态错乱,一定要设置状态无论什么状态，if和else是少不了的
        //Item子控件的点击事件
        helper.addOnClickListener(R.id.tweetName);
        //Item子控件的长按事件
        helper.addOnLongClickListener(R.id.tweetDate);
    }

}
