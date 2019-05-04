package org.gmfbilu.superapp.module_view.recyclerView.easyRecyclerView.multiType;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.view.recyclerView.BaseRecyclerView;
import org.gmfbilu.superapp.module_view.R;

import java.util.ArrayList;


public class MultiTypeRecyclerViewFragment extends BaseFragment {

    private BaseRecyclerView mBaseRecyclerView;
    private MultiTypeAdapter mMultiTypeAdapter;

    public static MultiTypeRecyclerViewFragment newInstance() {
        Bundle args = new Bundle();
        MultiTypeRecyclerViewFragment fragment = new MultiTypeRecyclerViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mBaseRecyclerView = view.findViewById(R.id.recyclerView);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_recyclerview_multi_type;
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
        mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mBaseRecyclerView.setProgressView(R.layout.module_view_recyclerview_progress);
        mMultiTypeAdapter = new MultiTypeAdapter(_mActivity);
        ArrayList<Object> arrAll = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Person person = new Person();
            person.name = "person" + i;
            person.age = i + 30;
            if (i == 0 || i == 3 || i == 8) {
                Ad ad = new Ad();
                ad.url = "aaaaaddddddddd";
                arrAll.add(ad);
            }
            arrAll.add(person);
        }

        mMultiTypeAdapter.addAll(arrAll);
        mBaseRecyclerView.setAdapterWithProgress(mMultiTypeAdapter);
    }
}
