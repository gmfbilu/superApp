package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class StockMarketRightAdapter extends BaseQuickAdapter<StockMarketBean, BaseViewHolder> {

    public StockMarketRightAdapter(int layoutResId, ArrayList<StockMarketBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StockMarketBean item) {
        TextView tv_item1_top = helper.getView(R.id.tv_item1_top);
        TextView tv_item1_bottom = helper.getView(R.id.tv_item1_bottom);
        TextView tv_item2_top = helper.getView(R.id.tv_item2_top);
        TextView tv_item2_bottom = helper.getView(R.id.tv_item2_bottom);
        TextView tv_item3_top = helper.getView(R.id.tv_item3_top);
        TextView tv_item3_bottom = helper.getView(R.id.tv_item3_bottom);
        TextView tv_item4_top = helper.getView(R.id.tv_item4_top);
        TextView tv_item5_top = helper.getView(R.id.tv_item5_top);
        TextView tv_item5_bottom = helper.getView(R.id.tv_item5_bottom);
        TextView tv_item6_top = helper.getView(R.id.tv_item6_top);
        tv_item1_top.setText(item.item1Top);
        tv_item1_bottom.setText(item.item1Bottom);
        tv_item2_top.setText(item.item2Top);
        tv_item2_bottom.setText(item.item2Bottom);
        tv_item3_top.setText(item.item3Top);
        tv_item3_bottom.setText(item.item3Bottom);
        tv_item4_top.setText(item.item4Top);
        tv_item5_top.setText(item.item5Top);
        tv_item5_bottom.setText(item.item5Bottom);
        tv_item6_top.setText(item.item6Top);
    }
}
