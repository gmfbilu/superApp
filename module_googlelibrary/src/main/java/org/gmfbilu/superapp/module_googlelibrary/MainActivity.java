package org.gmfbilu.superapp.module_googlelibrary;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.lib_base.ARouterPath;
import org.gmfbilu.lib_base.base.BaseActivity;

@Route(path = ARouterPath.MODULE_GOOGLELIBRARY)
public class MainActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.googlelibrary_activity_main;
    }

    @Override
    public void onClick(View v) {

    }
}
