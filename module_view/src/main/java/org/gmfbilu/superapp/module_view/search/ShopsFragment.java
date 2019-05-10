package org.gmfbilu.superapp.module_view.search;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.search.viewholder.ShopsViewHolder;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ShopsFragment extends BaseSearchFragment {

    private TextView tv_synthesize;
    private TextView tv_sales_volume;
    private TextView tv_sales_star;
    private ImageView iv_synthesize;
    private ImageView iv_sales_volume;
    private ImageView iv_sales_star;

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;
    private int lastIndex;

    public static ShopsFragment newInstance() {
        Bundle args = new Bundle();
        ShopsFragment fragment = new ShopsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
        tv_synthesize = view.findViewById(R.id.tv_synthesize);
        tv_sales_volume = view.findViewById(R.id.tv_sales_volume);
        tv_sales_star = view.findViewById(R.id.tv_sales_star);
        iv_synthesize = view.findViewById(R.id.iv_synthesize);
        iv_sales_volume = view.findViewById(R.id.iv_sales_volume);
        iv_sales_star = view.findViewById(R.id.iv_sales_star);
        view.findViewById(R.id.cl_synthesize).setOnClickListener(this);
        view.findViewById(R.id.cl_sales_volume).setOnClickListener(this);
        view.findViewById(R.id.cl_sales_star).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_shops;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cl_synthesize) {
            sortByCondition(0);
        } else if (id == R.id.cl_sales_volume) {
            sortByCondition(1);
        } else if (id == R.id.cl_sales_star) {
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
        if (index != 1)
            return;
        Toast.makeText(_mActivity, index + ", " + keyWord, Toast.LENGTH_SHORT).show();
    }


    private void initRecyclerView() {
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopsViewHolder(parent);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "1");
        }
        mAdapter.addAll(strings);
    }

    private void sortByCondition(int index) {
        if (index == lastIndex)
            return;
        tv_synthesize.setTextColor(index == 0 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_sales_volume.setTextColor(index == 1 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_sales_star.setTextColor(index == 2 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        iv_synthesize.setBackgroundResource(index == 0 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_sales_volume.setBackgroundResource(index == 1 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_sales_star.setBackgroundResource(index == 2 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        lastIndex = index;
    }
}
