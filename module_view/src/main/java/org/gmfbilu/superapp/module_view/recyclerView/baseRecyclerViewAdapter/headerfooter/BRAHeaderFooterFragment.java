package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.headerfooter;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class BRAHeaderFooterFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private HeaderFooterAdapter mHeaderFooterAdapter;

    public static BRAHeaderFooterFragment newInstance() {
        Bundle bundle = new Bundle();
        BRAHeaderFooterFragment braSimpleFragment = new BRAHeaderFooterFragment();
        braSimpleFragment.setArguments(bundle);
        return braSimpleFragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_bra_header_footer;
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
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mHeaderFooterAdapter = new HeaderFooterAdapter(R.layout.module_view_recyclerview_item_bras_simple, null);
        recyclerView.setAdapter(mHeaderFooterAdapter);
    }


}
