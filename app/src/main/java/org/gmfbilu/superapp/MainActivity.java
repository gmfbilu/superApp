package org.gmfbilu.superapp;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import org.gmfbilu.superapp.lib_base.app.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;


public class MainActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        findViewById(R.id.module_views).setOnClickListener(this);
        findViewById(R.id.module_util).setOnClickListener(this);
        findViewById(R.id.module_java).setOnClickListener(this);
        findViewById(R.id.module_kotlin).setOnClickListener(this);
        findViewById(R.id.module_netEasyCloudCourse).setOnClickListener(this);

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
            //跳转直接报错There is no route match the path [/xxx/xxx], in group [xxx][ ]，不管是kotlin页面跳java页面还是java 跳java页面。都报这个错
            //apply plugin: 'kotlin-kapt' 把这一句注释掉就好了，不过目前项目还是使用的java的ARouter，暂时还没有导入kotlin的ARouter。因为ARouter使用Kotlin，之前的java页面都要改
            ARouter.getInstance().build(ARouterPath.MODULE_KOTLIN).navigation();
        } else if (id == R.id.module_netEasyCloudCourse) {
            ARouter.getInstance().build(ARouterPath.MODULE_NetEasyCloudCourse).navigation();
        }
    }

}
