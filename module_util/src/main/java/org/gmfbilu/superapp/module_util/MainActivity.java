package org.gmfbilu.superapp.module_util;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.lib_base.ARouterPath;
import org.gmfbilu.lib_base.base.BaseActivity;

@Route(path = ARouterPath.MODULE_VIEWS)
public class MainActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {

    }

    @Override
    public int setLayout() {
        return R.layout.util_activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
