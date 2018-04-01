package org.gmfbilu.superapp.module_test;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.superapp.lib_base.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;


@Route(path = ARouterPath.MODULE_TEST)
public class MainActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        long key1 = getIntent().getLongExtra("key1", 0);

    }

    @Override
    public int setLayout() {
        return R.layout.module_test_activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
