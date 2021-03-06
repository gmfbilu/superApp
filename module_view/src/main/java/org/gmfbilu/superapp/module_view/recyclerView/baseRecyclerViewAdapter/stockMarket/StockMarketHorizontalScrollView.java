package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class StockMarketHorizontalScrollView extends HorizontalScrollView {

    /**
     * 产生联动的view对象
     */
    private View mSyncView;

    public StockMarketHorizontalScrollView(Context context) {
        super(context);
    }

    public StockMarketHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StockMarketHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //让需要联动的view也设置相同的滚动
        if(mSyncView != null) {
            mSyncView.scrollTo(l, t);
        }
    }

    public View getmSyncView() {
        return mSyncView;
    }

    public void setmSyncView(View mSyncView) {
        this.mSyncView = mSyncView;
    }
}
