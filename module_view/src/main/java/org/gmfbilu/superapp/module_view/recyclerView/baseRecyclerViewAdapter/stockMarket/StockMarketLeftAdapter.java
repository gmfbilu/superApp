package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class StockMarketLeftAdapter extends BaseQuickAdapter<StockMarketBean, BaseViewHolder> {

    public StockMarketLeftAdapter(int layoutResId, ArrayList<StockMarketBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StockMarketBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_code = helper.getView(R.id.tv_code);
        tv_name.setText(item.name);
        tv_code.setText(item.code);
    }
}
