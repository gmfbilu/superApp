package org.gmfbilu.superapp.module_view.fragment.module.secondTab;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.fragment.base.BaseMainFFragment;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class MainSecondFragment extends BaseMainFFragment {

    private TabLayout mTab;
    private ViewPager mViewPager;


    public static MainSecondFragment newInstance() {
        Bundle args = new Bundle();
        MainSecondFragment fragment = new MainSecondFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mTab = view.findViewById(R.id.module_fragment_tab);
        mViewPager = view.findViewById(R.id.module_fragment_viewPager);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_tab_second;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mViewPager.setAdapter(new SecondTabPagerFragmentAdapter(getChildFragmentManager(), "tab1", "tab2","tab3"));
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {

    }
}
