package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.simple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;

public class BRASimpleFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private SimpleAdapter mSimpleAdapter;

    public static BRASimpleFragment newInstance() {
        Bundle bundle = new Bundle();
        BRASimpleFragment braSimpleFragment = new BRASimpleFragment();
        braSimpleFragment.setArguments(bundle);
        return braSimpleFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_base_recyclerview_adapter_simple;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mSimpleAdapter = new SimpleAdapter(getStringData(20));
        mSimpleAdapter.openLoadAnimation();
        int mFirstPageItemCount = 3;
        mSimpleAdapter.setNotDoAnimationCount(mFirstPageItemCount);
        mSimpleAdapter.isFirstOnly(false);
        mSimpleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show("onItemClick：点击了第" + position + "个");
            }
        });
        mSimpleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String item = (String) adapter.getItem(position);
                int id = view.getId();
                if (id == R.id.img) {
                    ToastUtil.show("onItemChildClick：点击了第" + position + "个图片");
                }
            }
        });
        recyclerView.setAdapter(mSimpleAdapter);
    }

    private ArrayList<String> getStringData(int i) {
        ArrayList<String> data = new ArrayList<>();
        for (int i1 = 0; i1 < i; i1++) {
            data.add("" + i);
        }
        return data;
    }
}
