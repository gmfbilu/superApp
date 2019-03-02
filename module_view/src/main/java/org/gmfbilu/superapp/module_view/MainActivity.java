package org.gmfbilu.superapp.module_view;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;


@Route(path = ARouterPath.MODULE_VIEWS)
public class MainActivity extends BaseActivity {


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
}
