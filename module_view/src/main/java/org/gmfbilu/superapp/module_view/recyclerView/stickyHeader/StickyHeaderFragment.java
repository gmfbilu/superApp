package org.gmfbilu.superapp.module_view.recyclerView.stickyHeader;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.DividerDecoration;
import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.StickyHeaderDecoration;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class StickyHeaderFragment extends BaseFragment {

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;
    private StickyHeaderAdapter mStickyHeaderAdapter;

    public static StickyHeaderFragment newInstance() {
        Bundle args = new Bundle();
        StickyHeaderFragment fragment = new StickyHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_recyclerview_sticky_header;
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
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, AppUtils.dp2px(0.5f), AppUtils.dp2px(72), 0);
        itemDecoration.setDrawLastItem(false);
        mBaseRecyclerView.addItemDecoration(itemDecoration);
        mBaseRecyclerView.setRefreshingColorResources(R.color.lib_base_colorPrimaryDark);
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new StickHeaderViewHolder(parent);
            }
        });
        mBaseRecyclerView.setRefreshing(true);
        mBaseRecyclerView.setRefreshListener(this::refresh);
        mAdapter.setOnItemClickListener(position -> {

        });
        mStickyHeaderAdapter = new StickyHeaderAdapter(_mActivity);
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(mStickyHeaderAdapter);
        decoration.setIncludeHeader(false);
        mBaseRecyclerView.addItemDecoration(decoration);
        refresh();
    }

    private void refresh() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            strings.add(i + "");
        }
        mAdapter.addAll(strings);
        mStickyHeaderAdapter.setData(strings);
    }
}
