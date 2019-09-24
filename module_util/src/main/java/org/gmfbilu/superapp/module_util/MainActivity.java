package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.mvp.BaseMVPActivity;


@Route(path = ARouterPath.MODULE_UTIL)
public class MainActivity extends BaseMVPActivity<MainView,MainPresenterImpl> implements MainView {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.lib_base__fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.module_lib_base_fl_container;
    }

    @Override
    public void onClick(View v) {
    }


    @NonNull
    @Override
    public MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getData() {

    }
}
