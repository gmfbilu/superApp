package org.gmfbilu.superapp.module_util.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.lib_base.utils.Utils;
import org.gmfbilu.superapp.module_util.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends BaseFragment {


    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;


    public static GoodsFragment newInstance() {
        Bundle args = new Bundle();
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_goods;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBaseRecyclerView.setLayoutManager(layoutManager);
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new SearchHotViewHolder(parent);
            }
        });
        mAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(_mActivity);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv.setPadding(0, Utils.dp2px(_mActivity, 20), 0, 0);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setTextColor(Color.parseColor("#999999"));
                tv.setText("热门搜索");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                LayoutInflater layoutInflater = _mActivity.getLayoutInflater();
                View inflate = layoutInflater.inflate(R.layout.module_util_recyclerview_inflate_footer_search_center_searchhistory, null);
                ImageView iv_clear_search_history = inflate.findViewById(R.id.iv_clear_search_history);
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerArrayAdapter<String> mAdapter;
                BaseRecyclerView recyclerView = new BaseRecyclerView(parent.getContext()) {
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                //使用FlowLayout来做
/*                GridLayoutManager layoutManager = new GridLayoutManager(_mActivity, 2);
                layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return position == 0 ? 2 : 1;
                    }
                });
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        return new SearchHistoryViewHolder(parent);
                    }
                });
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    strings.add(i + "FUCK 糟蛋");
                }
                mAdapter.addAll(strings);*/
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "1111111111111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        mAdapter.addAll(strings);
    }

    private int setSpanSize(int position, List<String> listEntities) {
        int count;
        //设置item数据大于多少字只显示一行  默认 超过九个字的程度只显示一列
        final int MAX = 9;
        if (listEntities.get(position).length() > MAX) {
            count = 2;
        } else {
            count = 1;
        }

        return count;
    }
}
