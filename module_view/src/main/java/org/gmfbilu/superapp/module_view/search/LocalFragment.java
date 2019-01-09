package org.gmfbilu.superapp.module_view.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.view.dialog.DialogHelper;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.search.viewholder.LocalAllBigViewHolder;
import org.gmfbilu.superapp.module_view.search.viewholder.LocalAllSmallViewHolder;
import org.gmfbilu.superapp.module_view.search.viewholder.LocalViewHolder;

import java.util.ArrayList;

public class LocalFragment extends BaseSearchFragment {

    private TextView tv_all;
    private TextView tv_trading_area;
    private TextView tv_distance;
    private TextView tv_discount;
    private ImageView iv_all;
    private ImageView iv_trading_area;
    private ImageView iv_distance;
    private ImageView iv_discount;
    private ConstraintLayout cl_all;

    private BaseRecyclerView mBaseRecyclerView;
    private RecyclerArrayAdapter<String> mAdapter;
    private int lastIndex;
    private DialogHelper mCalDialog;

    private BaseRecyclerView recyclerView_big;
    private RecyclerArrayAdapter<String> mAdapter_big;
    private BaseRecyclerView recyclerView_small;
    private RecyclerArrayAdapter<String> mAdapter_small;
    private ArrayMap<String, ArrayList<String>> data;

    public static LocalFragment newInstance() {
        Bundle args = new Bundle();
        LocalFragment fragment = new LocalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
        tv_all = view.findViewById(R.id.tv_all);
        tv_trading_area = view.findViewById(R.id.tv_trading_area);
        tv_distance = view.findViewById(R.id.tv_distance);
        tv_discount = view.findViewById(R.id.tv_discount);
        iv_all = view.findViewById(R.id.iv_all);
        iv_trading_area = view.findViewById(R.id.iv_trading_area);
        iv_distance = view.findViewById(R.id.iv_distance);
        iv_discount = view.findViewById(R.id.iv_discount);
        cl_all = view.findViewById(R.id.cl_all);
        view.findViewById(R.id.cl_all).setOnClickListener(this);
        view.findViewById(R.id.cl_trading_area).setOnClickListener(this);
        view.findViewById(R.id.cl_distance).setOnClickListener(this);
        view.findViewById(R.id.cl_discount).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_local;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cl_all) {
            sortByCondition(0);
        } else if (id == R.id.cl_trading_area) {
            sortByCondition(1);
        } else if (id == R.id.cl_distance) {
            sortByCondition(2);
        } else if (id == R.id.cl_discount) {
            sortByCondition(3);
        } else if (id == R.id.v_half_transparent) {
            mCalDialog.dismiss();
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRecyclerView();
        data = new ArrayMap<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<String> small = new ArrayList<>();
            for (int i1 = 0; i1 < 6; i1++) {
                small.add(i + "and" + i1);
            }
            data.put(i + "", small);
        }
        Logger.d(data);
    }


    @Override
    public void search(int index, String keyWord) {
        if (index != 2)
            return;
        Toast.makeText(_mActivity, index + ", " + keyWord, Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<String>(_mActivity) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new LocalViewHolder(parent);
            }
        });
        mAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "1111111111111aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        }
        mAdapter.addAll(strings);
    }

    private void sortByCondition(int index) {
        if (index == 0) {
            showSelectDialog();
        }
        if (index == lastIndex)
            return;
        tv_all.setTextColor(index == 0 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_trading_area.setTextColor(index == 1 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_distance.setTextColor(index == 2 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_discount.setTextColor(index == 3 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        iv_all.setBackgroundResource(index == 0 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_trading_area.setBackgroundResource(index == 1 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_distance.setBackgroundResource(index == 2 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        iv_discount.setBackgroundResource(index == 3 ? R.mipmap.search_select_arrow_red : R.mipmap.search_select_arrow);
        lastIndex = index;
    }

    private void showSelectDialog() {
        if (mCalDialog != null && mCalDialog.isShowing()) {
            return;
        }
        if (mCalDialog == null) {
            mCalDialog = new DialogHelper(_mActivity);
            View layout = mCalDialog.setSearch_Local_All_Layout(R.layout.module_view_dialog_search_local_all, cl_all);
            recyclerView_big = layout.findViewById(R.id.recyclerView_big);
            recyclerView_small = layout.findViewById(R.id.recyclerView_small);
            layout.findViewById(R.id.v_half_transparent).setOnClickListener(this);
            initBigRecyclerView();
            initSmallRecyclerView();
        }
        if (!mCalDialog.isShowing()) {
            mCalDialog.show();
        }
    }


    private void initBigRecyclerView() {
        if (mAdapter_big == null) {
            recyclerView_big.setLayoutManager(new LinearLayoutManager(_mActivity));
            recyclerView_big.setAdapterWithProgress(mAdapter_big = new RecyclerArrayAdapter<String>(_mActivity) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new LocalAllBigViewHolder(parent);
                }
            });
            mAdapter_big.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    // TODO: 2019/1/7 刷新每个small的数据
                    updateSmall(position);
                }
            });
            int size = data.size();
            ArrayList<String> strings = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                String s = data.keyAt(i);
                strings.add(s);
            }
            mAdapter_big.addAll(strings);
        }
    }

    private void initSmallRecyclerView() {
        if (mAdapter_small == null) {
            recyclerView_small.setLayoutManager(new LinearLayoutManager(_mActivity));
            recyclerView_small.setAdapterWithProgress(mAdapter_small = new RecyclerArrayAdapter<String>(_mActivity) {
                @Override
                public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                    return new LocalAllSmallViewHolder(parent);
                }
            });
            mAdapter_small.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {

                }
            });
        }
        // TODO: 2019/1/8 每次获取对应数据
        updateSmall(0);
    }

    private void updateSmall(int position) {
        mAdapter_small.clear();
        ArrayList<String> strings = data.valueAt(position);
        mAdapter_small.addAll(strings);
        Logger.d(strings);
    }

}
