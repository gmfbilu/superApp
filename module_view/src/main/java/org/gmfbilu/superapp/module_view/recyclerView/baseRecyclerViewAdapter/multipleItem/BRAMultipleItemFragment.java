package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.multipleItem;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class BRAMultipleItemFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MultipleItemQuickAdapter mMultipleItemQuickAdapter;

    public static BRAMultipleItemFragment newInstance() {
        Bundle args = new Bundle();
        BRAMultipleItemFragment fragment = new BRAMultipleItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mMultipleItemQuickAdapter = new MultipleItemQuickAdapter(null);
        recyclerView.setAdapter(mMultipleItemQuickAdapter);
        mMultipleItemQuickAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int headerLayoutCount = mMultipleItemQuickAdapter.getHeaderLayoutCount();
                int footerLayoutCount = mMultipleItemQuickAdapter.getFooterLayoutCount();
                //这个框架直接过滤的值，Adapter.getItem(position)不管有没有Header都会得到正确的值
                //Adapter.getHeaderLayoutCount()和Adapter.getFooterLayoutCount()都一直是1，why?
                LoggerUtil.d("Header有" + headerLayoutCount + "个，Foot有" + footerLayoutCount + "个，点击的是第" + position + "，值是" + mMultipleItemQuickAdapter.getItem(position));
            }
        });

        mMultipleItemQuickAdapter.setNewData(getData());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_multiple;
    }

    @Override
    public void onClick(View v) {

    }

    private ArrayList<MultipleItem> getData() {
        ArrayList<MultipleItem> multipleItems = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            int i1 = i % 4;
            MultipleItem multipleItem = null;
            if (i1 == 0) {
                multipleItem = new MultipleItem(MultipleItem.IMG);
                multipleItem.picDes="远足壁纸";
                multipleItem.picUrl="https://c.pxhere.com/photos/1c/d0/adventure_clouds_cloudscape_dawn_daylight_dusk_evening_fog-1528441.jpg!d";
            }else {
                multipleItem = new MultipleItem(MultipleItem.TEXT);
                multipleItem.name="batman";
                multipleItem.age=i+"";
            }
            multipleItems.add(multipleItem);
        }
        return multipleItems;
    }
}
