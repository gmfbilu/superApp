package org.gmfbilu.superapp.module_view.kLine.MPChart.land;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.kLine.MPChart.MPChartKlineWuDangDetailFragment;
import org.gmfbilu.superapp.module_view.kLine.MPChart.MPChartKlineWuDangFragment;
import org.gmfbilu.superapp.module_view.kLine.MPChart.SimpleFragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import me.jessyan.autosize.internal.CancelAdapt;

public class MPChartLandActivity extends BaseActivity  implements CancelAdapt {

    private TabLayout tab1,tab2;
    private ViewPager viewpager1,viewpager2;

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        viewpager1 = findViewById(R.id.viewpager1);
        viewpager2 = findViewById(R.id.viewpager2);
        initTab1();
        initTab2();

    }

    @Override
    public int setLayout() {
        return R.layout.module_view_activity_mpchart_land;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void orientation() {
    }

    private void initTab1(){
        Fragment[] fragments = {MPChartKlineWuDangFragment.newInstance(), MPChartKlineWuDangDetailFragment.newInstance()};
        String[] titles = {"五档", "明细"};
        viewpager1.setOffscreenPageLimit(fragments.length);
        viewpager1.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tab1.setupWithViewPager(viewpager1);
    }

    private void initTab2(){
        Fragment[] fragments = {LandOneDayFragment.newInstance(), LandFiveDayFragment.newInstance(),LandKlineFragment.newInstance(1),LandKlineFragment.newInstance(2),LandKlineFragment.newInstance(3)};
        String[] titles = {"分时", "5日","日K","周K","月K"};
        viewpager2.setOffscreenPageLimit(fragments.length);
        viewpager2.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tab2.setupWithViewPager(viewpager2);
    }


}
