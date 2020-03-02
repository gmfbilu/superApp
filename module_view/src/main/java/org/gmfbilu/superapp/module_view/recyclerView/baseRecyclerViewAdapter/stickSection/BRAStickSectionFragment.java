package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.OnItemClickListener;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.RecyclerViewAdapter;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stick.StickyHeadEntity;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration.OnStickyChangeListener;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration.SpaceItemDecoration;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration.StickyHeadContainer;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.stickyitemdecoration.StickyItemDecoration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class BRAStickSectionFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private StockAdapter mAdapter;
    private StickyHeadContainer mStickyHeadContainer;
    private int mStickyPosition;


    public static BRAStickSectionFragment newInstance() {
        Bundle args = new Bundle();
        BRAStickSectionFragment fragment = new BRAStickSectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        mStickyHeadContainer = view.findViewById(R.id.shc);
        final TextView tvStockName = mStickyHeadContainer.findViewById(R.id.tv_stock_name);
        final CheckBox checkBox = mStickyHeadContainer.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mAdapter.getData().get(mStickyPosition).getData().check = isChecked;
                mAdapter.notifyItemChanged(mStickyPosition);
            }
        });
        mStickyHeadContainer.setDataCallback(new StickyHeadContainer.DataCallback() {
            @Override
            public void onDataChange(int pos) {
                mStickyPosition = pos;
                StockEntity.StockInfo item = mAdapter.getData().get(pos).getData();
                tvStockName.setText(item.stickyHeadName);
                checkBox.setChecked(item.check);
            }
        });
        mStickyHeadContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));

        StickyItemDecoration stickyItemDecoration = new StickyItemDecoration(mStickyHeadContainer, RecyclerViewAdapter.TYPE_STICKY_HEAD);
        stickyItemDecoration.setOnStickyChangeListener(new OnStickyChangeListener() {
            @Override
            public void onScrollable(int offset) {
                mStickyHeadContainer.scrollChild(offset);
                mStickyHeadContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onInVisible() {
                mStickyHeadContainer.reset();
                mStickyHeadContainer.setVisibility(View.INVISIBLE);
            }
        });

        recyclerView.addItemDecoration(stickyItemDecoration);
        recyclerView.addItemDecoration(new SpaceItemDecoration(recyclerView.getContext()));
        mAdapter = new StockAdapter(null);
        mAdapter.setItemClickListener(new OnItemClickListener<StockEntity.StockInfo>() {
            @Override
            public void onItemClick(View view, StockEntity.StockInfo data, int position) {
                LoggerUtil.d("点击了Item:" + position + ", data:" + data);
            }
        });
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(parseAndSetData());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_stick_section;
    }

    @Override
    public void onClick(View v) {

    }


    private List<StickyHeadEntity<StockEntity.StockInfo>> parseAndSetData() {
        String result = getStrFromAssets("rasking.json");
        Gson gson = new Gson();

        final StockEntity stockEntity = gson.fromJson(result, StockEntity.class);

        List<StockEntity.StockInfo> data = new ArrayList<>();

        data.add(new StockEntity.StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "涨幅榜"));
        for (StockEntity.StockInfo info : stockEntity.increase_list) {
            info.setItemType(RecyclerViewAdapter.TYPE_DATA);
            data.add(info);
        }

        data.add(new StockEntity.StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "跌幅榜"));
        for (StockEntity.StockInfo info : stockEntity.down_list) {
            info.setItemType(RecyclerViewAdapter.TYPE_DATA);
            data.add(info);
        }

        data.add(new StockEntity.StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "换手率"));
        for (StockEntity.StockInfo info : stockEntity.change_list) {
            info.setItemType(RecyclerViewAdapter.TYPE_DATA);
            data.add(info);
        }

        data.add(new StockEntity.StockInfo(RecyclerViewAdapter.TYPE_STICKY_HEAD, "振幅榜"));
        for (StockEntity.StockInfo info : stockEntity.amplitude_list) {
            info.setItemType(RecyclerViewAdapter.TYPE_DATA);
            data.add(info);
        }

        List<StickyHeadEntity<StockEntity.StockInfo>> list = new ArrayList<>(data.size());
        list.add(new StickyHeadEntity<StockEntity.StockInfo>(null, StockAdapter.TYPE_HEAD, null));
        for (StockEntity.StockInfo info : data) {
            list.add(new StickyHeadEntity<>(info, info.getItemType(), info.stickyHeadName));
        }
        return list;
    }


    /**
     * @return Json数据（String）
     * @description 通过assets文件获取json数据，这里写的十分简单，没做循环判断。
     */
    public String getStrFromAssets(String name) {
        AssetManager assetManager = _mActivity.getAssets();
        try {
            InputStream is = assetManager.open(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

}
