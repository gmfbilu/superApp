package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class StockMarketFragment extends BaseFragment {

    private StockMarketHorizontalScrollView rightTitleHorscrollView, rightContentHorscrollView;
    private StockMarketRecyclerView contentListViewLeft, contentListViewRight;
    private StockMarketLeftAdapter leftAdapter;
    private StockMarketRightAdapter rightAdapter;
    private int Page = 0;

    public static StockMarketFragment newInstance() {
        Bundle bundle = new Bundle();
        StockMarketFragment braSimpleFragment = new StockMarketFragment();
        braSimpleFragment.setArguments(bundle);
        return braSimpleFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        rightTitleHorscrollView = view.findViewById(R.id.rightTitleHorscrollView);
        rightContentHorscrollView = view.findViewById(R.id.rightContentHorscrollView);
        contentListViewLeft = view.findViewById(R.id.contentListViewLeft);
        contentListViewRight = view.findViewById(R.id.contentListViewRight);
        //相互引用
        rightTitleHorscrollView.setmSyncView(rightContentHorscrollView);
        rightContentHorscrollView.setmSyncView(rightTitleHorscrollView);

        contentListViewLeft.setHasFixedSize(true);
        contentListViewLeft.setLayoutManager(new LinearLayoutManager(_mActivity));
        leftAdapter = new StockMarketLeftAdapter(R.layout.module_view_recyclerview_item_stackmarket_left, null);
        //在布局不能填满一页情况下,会触发加载更多
        //loadMoreComplete();表示当次加载完毕。后面可能有数据
        //loadMoreEnd(true)和loadMoreEnd(FALSE);表示后面没有数据了，最后一页
       leftAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ArrayList<StockMarketBean> stockMarketBeans = generateData();
                leftAdapter.addData(stockMarketBeans);
                LoggerUtil.d("leftAdapter setOnLoadMoreListener");
            }
        }, contentListViewLeft);
        contentListViewLeft.setAdapter(leftAdapter);

        contentListViewRight.setHasFixedSize(true);
        contentListViewRight.setLayoutManager(new LinearLayoutManager(_mActivity));
        rightAdapter = new StockMarketRightAdapter(R.layout.module_view_recyclerview_item_stackmarket_right, null);
        //在布局不能填满一页情况下,会触发加载更多
        rightAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                ArrayList<StockMarketBean> stockMarketBeans = generateData();
                rightAdapter.addData(stockMarketBeans);
                LoggerUtil.d("rightAdapter setOnLoadMoreListener");
            }
        }, contentListViewRight);
        contentListViewRight.setAdapter(rightAdapter);

        //两个RecyclerView同时滑动
        contentListViewLeft.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    contentListViewRight.scrollBy(dx, dy);
                }
            }
        });
        contentListViewRight.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    contentListViewLeft.scrollBy(dx, dy);
                }
            }
        });


        ArrayList<StockMarketBean> stockMarketBeans = generateData();
        leftAdapter.addData(stockMarketBeans);
        rightAdapter.addData(stockMarketBeans);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_stock_market;
    }

    @Override
    public void onClick(View v) {

    }

    private ArrayList<StockMarketBean> generateData() {
        ArrayList<StockMarketBean> stockMarketBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StockMarketBean stockMarketBean = new StockMarketBean();
            stockMarketBean.name = "福达合金" + Page + "--" + i;
            stockMarketBean.code = i + "";
            stockMarketBean.item1Top = "410.00";
            stockMarketBean.item1Bottom = "410.00";
            stockMarketBean.item2Top = "68000";
            stockMarketBean.item2Bottom = "68000";
            stockMarketBean.item3Top = "买入";
            stockMarketBean.item3Bottom = "已成";
            stockMarketBean.item4Top = "20191111";
            stockMarketBean.item5Top = "20191111";
            stockMarketBean.item5Bottom = "13:12:10";
            stockMarketBean.item6Top = "- -";
            stockMarketBeans.add(stockMarketBean);
        }
        Page = Page + 1;
        return stockMarketBeans;
    }

}
