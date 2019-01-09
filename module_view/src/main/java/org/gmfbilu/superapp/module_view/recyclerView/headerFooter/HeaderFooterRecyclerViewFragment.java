package org.gmfbilu.superapp.module_view.recyclerView.headerFooter;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.BaseViewHolder;
import org.gmfbilu.superapp.lib_base.view.recyclerView.adapter.RecyclerArrayAdapter;
import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.DividerDecoration;
import org.gmfbilu.superapp.lib_base.view.recyclerView.decoration.SpaceDecoration;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.recyclerView.simple.SimpleBeen;

import java.util.ArrayList;

public class HeaderFooterRecyclerViewFragment extends BaseFragment {


    private BaseRecyclerView mBaseRecyclerView;
    private HeaderFooterAdapter mHeaderFooterAdapter;

    public static HeaderFooterRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        HeaderFooterRecyclerViewFragment fragment = new HeaderFooterRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_recyclerview_header_foot;
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
        mBaseRecyclerView.setRefreshingColorResources(R.color.lib_base_colorPrimaryDark);
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setAdapterWithProgress(mHeaderFooterAdapter = new HeaderFooterAdapter(_mActivity));
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, AppUtils.dp2px(_mActivity, 16f), 0, 0);
        itemDecoration.setDrawLastItem(false);
        mBaseRecyclerView.addItemDecoration(itemDecoration);


        mHeaderFooterAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                TextView tv = new TextView(_mActivity);
                tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                tv.setText("第一个Header");
                return tv;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mHeaderFooterAdapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                RecyclerArrayAdapter<SimpleBeen> mAdapter;
                BaseRecyclerView recyclerView = new BaseRecyclerView(parent.getContext()) {
                    //为了不打扰横向RecyclerView的滑动操作，可以这样处理
                    @Override
                    public boolean onTouchEvent(MotionEvent event) {
                        super.onTouchEvent(event);
                        return true;
                    }
                };
                recyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.addItemDecoration(new SpaceDecoration(AppUtils.dp2px(_mActivity, 8)));
                recyclerView.setHorizontalScrollBarEnabled(false);
                recyclerView.setAdapterWithProgress(mAdapter = new RecyclerArrayAdapter<SimpleBeen>(_mActivity) {
                    @Override
                    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                        return new HeaderViewHolder(parent);
                    }
                });
                ArrayList<SimpleBeen> simpleBeens = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    SimpleBeen simpleBeen = new SimpleBeen();
                    simpleBeen.name = "第二个Header-" + i;
                    simpleBeens.add(simpleBeen);
                }
                mAdapter.addAll(simpleBeens);
                return recyclerView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        mHeaderFooterAdapter.addFooter(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                LayoutInflater layoutInflater = _mActivity.getLayoutInflater();
                View inflate = layoutInflater.inflate(R.layout.module_view_recyclerview_inflate_footer, null);
                TextView tv = inflate.findViewById(R.id.tv);
                tv.setText(" I AM A FOOTER");
                return inflate;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });

        ArrayList<SimpleBeen> simpleBeens = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SimpleBeen simpleBeen = new SimpleBeen();
            simpleBeen.name = i + "";
            simpleBeens.add(simpleBeen);
        }
        mHeaderFooterAdapter.addAll(simpleBeens);
    }

}
