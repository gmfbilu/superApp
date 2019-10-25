package org.gmfbilu.superapp;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;


public class MainActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        findViewById(R.id.module_views).setOnClickListener(this);
        findViewById(R.id.module_util).setOnClickListener(this);
        findViewById(R.id.module_java).setOnClickListener(this);
        findViewById(R.id.module_kotlin).setOnClickListener(this);
        ToastUtil.show(BuildConfig.appName);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
          /*加转场动画跳转 api>16
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
            ARouter.getInstance().build(ARouterPath.MODULE_TEST)
                    .withLong("key1", 100)
                    .withParcelable("key2", new TestBean(10, "jack"))
                    .withOptionsCompat(compat)
                    .navigation();*/

        if (id == R.id.module_views) {
            ARouter.getInstance().build(ARouterPath.MODULE_VIEWS).navigation();
        } else if (id == R.id.module_util) {
            ARouter.getInstance().build(ARouterPath.MODULE_UTIL).navigation();
        } else if (id == R.id.module_java) {
            ARouter.getInstance().build(ARouterPath.MODULE_JAVA).navigation();
        } else if (id == R.id.module_kotlin) {
            ARouter.getInstance().build(ARouterPath.MODULE_KOTLIN).navigation();
        }
    }

}
