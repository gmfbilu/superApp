package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

public class StockMarketRecyclerView extends RecyclerView {

    public StockMarketRecyclerView(Context context) {
        super(context);
    }

    public StockMarketRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StockMarketRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override

    /**
     * 重写该方法，ScrollView中嵌套ListView
     * 达到使ListView自适应高度
     */

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
