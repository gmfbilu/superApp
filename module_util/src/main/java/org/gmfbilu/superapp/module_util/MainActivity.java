package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.mvp.BaseMVPActivity;

import androidx.annotation.NonNull;


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


    // TODO: 2020/3/29 有bug,跳转后返回会多次调用 
    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
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

}
