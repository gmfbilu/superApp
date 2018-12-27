package org.gmfbilu.superapp.module_test;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;


@Route(path = ARouterPath.MODULE_TEST)
public class MainActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        long key1 = getIntent().getLongExtra("key1", 0);

    }

    @Override
    public int setLayout() {
        String a = "java";
        String b = "java";
        String c = "ja" + "va";
        Logger.d(a == b);
        Logger.d(a.equals(b));
        Logger.d(a == c);
        Logger.d(a.equals(c));
        return R.layout.module_test_activity_main;
    }

    @Override
    public void onClick(View v) {
        
    }
}
