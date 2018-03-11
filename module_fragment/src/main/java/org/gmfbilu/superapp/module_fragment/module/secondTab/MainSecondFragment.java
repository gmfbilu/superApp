package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.base.BaseMainFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class MainSecondFragment extends BaseMainFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;


    public static MainSecondFragment newInstance() {
        Bundle args = new Bundle();
        MainSecondFragment fragment = new MainSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_second, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTab = view.findViewById(R.id.tab);
        mViewPager = view.findViewById(R.id.viewPager);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPager.setAdapter(new SecondTabPagerFragmentAdapter(getChildFragmentManager(), "tab1", "tab2","tab3"));
        mTab.setupWithViewPager(mViewPager);
    }

}
