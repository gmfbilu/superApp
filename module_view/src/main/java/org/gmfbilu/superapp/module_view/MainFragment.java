package org.gmfbilu.superapp.module_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.amap.AmapFragment;
import org.gmfbilu.superapp.module_view.banner.BannerFragment;
import org.gmfbilu.superapp.module_view.constraintlayout.ConstraintLayoutFragment;
import org.gmfbilu.superapp.module_view.customViews.CustomViewsFragment;
import org.gmfbilu.superapp.module_view.dialogFragment.DialogFragment;
import org.gmfbilu.superapp.module_view.dynamicLayout.DynamicLayoutFragment;
import org.gmfbilu.superapp.module_view.fragment.module.FragmentActivity;
import org.gmfbilu.superapp.module_view.kLine.KLineFragment;
import org.gmfbilu.superapp.module_view.recyclerView.RecyclerViewFragment;
import org.gmfbilu.superapp.module_view.search.SearchFragment;
import org.gmfbilu.superapp.module_view.shape.ShapeFragment;
import org.gmfbilu.superapp.module_view.surfaceView.SurfaceViewFragment;
import org.gmfbilu.superapp.module_view.switch_checkbox_listpreference.Switch_CheckBox_ListPreferenceFragment;
import org.gmfbilu.superapp.module_view.test.TestActivity;
import org.gmfbilu.superapp.module_view.textView.TextViewFragment;
import org.gmfbilu.superapp.module_view.topbar.TopBarFragment;


/**
 * Created by gmfbilu on 18-3-11.
 * 主界面Fragment,根Fragment
 */


public class MainFragment extends BaseFragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_view_bt_test).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_fragment).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_constraintlayout).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_switch_checkbox_listpreference).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_dialogfragment).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_dynamicLayout).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_recyclerview).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_shape).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_textview).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_search).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_generalview).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_amap).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_banner).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_head).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_surfaceview).setOnClickListener(this);
        view.findViewById(R.id.module_view_bt_kline).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_main;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_view_bt_test) {
            startActivity(new Intent(_mActivity, TestActivity.class));
        } else if (id == R.id.module_view_bt_fragment) {
            startActivity(new Intent(_mActivity, FragmentActivity.class));
        } else if (id == R.id.module_view_bt_constraintlayout) {
            start(ConstraintLayoutFragment.newInstance());
        } else if (id == R.id.module_view_bt_switch_checkbox_listpreference) {
            start(Switch_CheckBox_ListPreferenceFragment.newInstance());
        } else if (id == R.id.module_view_bt_dialogfragment) {
            start(DialogFragment.newInstance());
        } else if (id == R.id.module_view_bt_dynamicLayout) {
            start(DynamicLayoutFragment.newInstance());
        } else if (id == R.id.module_view_bt_recyclerview) {
            start(RecyclerViewFragment.newInstance());
        } else if (id == R.id.module_view_bt_shape) {
            start(ShapeFragment.newInstance());
        } else if (id == R.id.module_view_bt_textview) {
            start(TextViewFragment.newInstance());
        } else if (id == R.id.module_view_bt_search) {
            start(SearchFragment.newInstance());
        } else if (id == R.id.module_view_bt_generalview) {
            start(CustomViewsFragment.newInstance());
        } else if (id == R.id.module_view_bt_amap) {
            start(AmapFragment.newInstance());
        } else if (id == R.id.module_view_bt_banner) {
            start(BannerFragment.newInstance());
        } else if (id == R.id.module_view_bt_head) {
            start(TopBarFragment.newInstance());
        } else if (id == R.id.module_view_bt_surfaceview) {
            start(SurfaceViewFragment.newInstance());
        } else if (id == R.id.module_view_bt_kline) {
            start(KLineFragment.newInstance());
        }
    }
}
