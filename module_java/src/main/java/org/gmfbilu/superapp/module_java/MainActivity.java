package org.gmfbilu.superapp.module_java;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.superapp.lib_base.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;

@Route(path = ARouterPath.MODULE_JAVA)
public class MainActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.module_java_fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.module_java_activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
