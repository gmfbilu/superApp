package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.headerfooter.BRAHeaderFooterFragment;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.multipleItem.BRAMultipleItemFragment;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.section.BRASectionFragment;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.simple.BRASimpleFragment;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stickSection.BRAStickSectionFragment;
import org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.stockMarket.StockMarketFragment;

public class BRAFragment extends BaseFragment {


    public static BRAFragment newInstance() {
        Bundle args = new Bundle();
        BRAFragment fragment = new BRAFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.bt_simple).setOnClickListener(this);
        view.findViewById(R.id.bt_header_footer).setOnClickListener(this);
        view.findViewById(R.id.bt_stockMarket).setOnClickListener(this);
        view.findViewById(R.id.bt_MultipleItem).setOnClickListener(this);
        view.findViewById(R.id.bt_Section).setOnClickListener(this);
        view.findViewById(R.id.bt_StickSection).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_simple) {
            start(BRASimpleFragment.newInstance());
        }else if (id==R.id.bt_header_footer){
            start(BRAHeaderFooterFragment.newInstance());
        }else if (id==R.id.bt_stockMarket){
            start(StockMarketFragment.newInstance());
        }else if (id==R.id.bt_MultipleItem){
            start(BRAMultipleItemFragment.newInstance());
        }else if (id==R.id.bt_Section){
            start(BRASectionFragment.newInstance());
        }else if (id==R.id.bt_StickSection){
            start(BRAStickSectionFragment.newInstance());
        }
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
    }
}
