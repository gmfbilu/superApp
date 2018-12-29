package org.gmfbilu.superapp.module_util.recyclerView.simple;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.lib_base.base.baseRecyclerView.decoration.DividerDecoration;
import org.gmfbilu.superapp.lib_base.utils.Utils;
import org.gmfbilu.superapp.module_util.R;

import java.util.ArrayList;

public class SimpleRecyclerViewFragment extends BaseFragment {

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<SimpleBeen> mAdapter;

    private Handler handler = new Handler();
    private int page = 0;
    private boolean hasNetWork = true;


    public static SimpleRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        SimpleRecyclerViewFragment fragment = new SimpleRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.tv_add).setOnClickListener(this);
        view.findViewById(R.id.tv_update).setOnClickListener(this);
        CheckBox viewById = view.findViewById(R.id.cb_net);
        viewById.setOnCheckedChangeListener((buttonView, isChecked) -> hasNetWork = isChecked);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_recyclerview_simple;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        SimpleBeen simpleBeen = new SimpleBeen();
        if (id == R.id.tv_add) {
            simpleBeen.name = "insert";
            mAdapter.insert(simpleBeen, 0);
        } else if (id == R.id.tv_update) {
            simpleBeen.name = "update";
            mAdapter.update(simpleBeen, 1);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        //刷新动画颜色
        mBaseRecyclerView.setRefreshingColorResources(R.color.lib_base_colorPrimaryDark);
        //布局
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        //简单数据下Adapter
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<SimpleBeen>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new SimpleViewHolder(parent);
            }
        });
        //间隔装饰
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, Utils.dip2px(_mActivity, 16f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mBaseRecyclerView.addItemDecoration(itemDecoration);
        //是否显示刷新动画
        mBaseRecyclerView.setRefreshing(true);
        //下拉刷新
        mBaseRecyclerView.setRefreshListener(this::refresh);
        //加载更多
        mAdapter.setMore(R.layout.module_util_recyclerview_content_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                handler.postDelayed(() -> {
                    //刷新
                    if (!hasNetWork) {
                        mAdapter.pauseMore();
                        return;
                    }
                    ArrayList<SimpleBeen> simpleBeens = new ArrayList<>();
                    if (page <= 3) {
                        for (int i = 0; i < 10; i++) {
                            SimpleBeen simpleBeen = new SimpleBeen();
                            simpleBeen.name = i + "";
                            simpleBeens.add(simpleBeen);
                        }
                    }
                    mAdapter.addAll(simpleBeens);
                    page++;
                }, 2000);
            }

            @Override
            public void onMoreClick() {

            }
        });
        //出错情况下
        mAdapter.setError(R.layout.module_util_recyclerview_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                mAdapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                mAdapter.resumeMore();
            }
        });
        //没有数据
        mBaseRecyclerView.setEmptyView(R.layout.module_util_recyclerview_empty);
        //下拉加载更多-没有更多数据
        mAdapter.setNoMore(R.layout.module_util_recyclerview_no_more);
        //item点击事件
        mAdapter.setOnItemClickListener(position -> {
            SimpleBeen item = mAdapter.getItem(position);
            Toast.makeText(_mActivity, item.name, Toast.LENGTH_SHORT).show();
        });
        //item长按事件
        mAdapter.setOnItemLongClickListener(position -> {
            //删除某一个item
            mAdapter.remove(position);
            return true;
        });
        refresh();
    }

    private void refresh() {
        page = 0;
        handler.postDelayed(() -> {
            mAdapter.clear();
            //刷新
            if (!hasNetWork) {
                mAdapter.pauseMore();
                return;
            }
            ArrayList<SimpleBeen> simpleBeens = new ArrayList<>();
            if (page <= 3) {
                for (int i = 0; i < 10; i++) {
                    SimpleBeen simpleBeen = new SimpleBeen();
                    simpleBeen.name = i + "";
                    simpleBeens.add(simpleBeen);
                }
            }
            mAdapter.addAll(simpleBeens);
            page = 1;
        }, 2000);
    }
}
