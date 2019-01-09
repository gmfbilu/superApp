package org.gmfbilu.superapp.module_view.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.SpaceDecoration;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.search.viewholder.GoodsViewHolder;

import java.util.ArrayList;

public class GoodsFragment extends BaseSearchFragment {

    private TextView tv_synthesize;
    private TextView tv_sales_volume;
    private TextView tv_sales_price;
    private ImageView iv_synthesize;
    private ImageView iv_sales_volume;
    private ImageView iv_sales_price;

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;
    private int lastIndex;

    public static GoodsFragment newInstance() {
        Bundle args = new Bundle();
        GoodsFragment fragment = new GoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
        tv_synthesize = view.findViewById(R.id.tv_synthesize);
        tv_sales_volume = view.findViewById(R.id.tv_sales_volume);
        tv_sales_price = view.findViewById(R.id.tv_sales_price);
        iv_synthesize = view.findViewById(R.id.iv_synthesize);
        iv_sales_volume = view.findViewById(R.id.iv_sales_volume);
        iv_sales_price = view.findViewById(R.id.iv_sales_price);
        view.findViewById(R.id.cl_synthesize).setOnClickListener(this);
        view.findViewById(R.id.cl_sales_volume).setOnClickListener(this);
        view.findViewById(R.id.cl_sales_price).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_goods;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cl_synthesize) {
            sortByCondition(0);
        } else if (id == R.id.cl_sales_volume) {
            sortByCondition(1);
        } else if (id == R.id.cl_sales_price) {
            sortByCondition(2);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void search(int index, String keyWord) {
        if (index != 0)
            return;
        mAdapter.clear();
        if (keyWord.equals("点此处展示搜索无结果")) {
            mBaseRecyclerView.showEmpty();
            return;
        }
        Toast.makeText(_mActivity, index + ", " + keyWord, Toast.LENGTH_SHORT).show();
        search(keyWord);
    }

    private void search(String keyWord) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + keyWord);
        }
        mAdapter.addAll(strings);
    }

    private void initRecyclerView() {
        mBaseRecyclerView.addItemDecoration(new SpaceDecoration(AppUtils.dp2px(_mActivity, 8)));
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mBaseRecyclerView.setLayoutManager(layoutManager);
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new GoodsViewHolder(parent);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mBaseRecyclerView.setEmptyView(R.layout.module_view_recyclerview_empty_search_center);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "");
        }
    }

    private void sortByCondition(int index) {
        if (index == lastIndex)
            return;
        tv_synthesize.setTextColor(index == 0 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_sales_volume.setTextColor(index == 1 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_sales_price.setTextColor(index == 2 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        iv_synthesize.setBackgroundResource(index == 0 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_sales_volume.setBackgroundResource(index == 1 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_sales_price.setBackgroundResource(index == 2 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        lastIndex = index;
    }

}
