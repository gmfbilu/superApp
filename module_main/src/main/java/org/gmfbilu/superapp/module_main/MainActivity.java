package org.gmfbilu.superapp.module_main;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leon.channel.helper.ChannelReaderUtil;

import org.gmfbilu.superapp.lib_base.ARouterPath;
import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.bean.TestBean;


public class MainActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Toast.makeText(this, channel + "damn~~", Toast.LENGTH_LONG).show();
        findViewById(R.id.module_main_test).setOnClickListener(this);
        findViewById(R.id.module_main_fragment).setOnClickListener(this);
        findViewById(R.id.module_main_googleLibrary).setOnClickListener(this);
        findViewById(R.id.module_main_views).setOnClickListener(this);
        findViewById(R.id.module_main_util).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_main_activity_main;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_main_test) {
            /**
             * 加转场动画跳转 api>16
             */
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
            ARouter.getInstance().build(ARouterPath.MODULE_TEST)
                    .withLong("key1", 100)
                    .withParcelable("key2", new TestBean(10, "jack"))
                    .withOptionsCompat(compat)
                    .navigation();

        } else if (id == R.id.module_main_fragment) {
            ARouter.getInstance().build(ARouterPath.MODULE_FRAGMENT).navigation();
        } else if (id == R.id.module_main_googleLibrary) {
            ARouter.getInstance().build(ARouterPath.MODULE_GOOGLELIBRARY).navigation();
        } else if (id == R.id.module_main_views) {
            ARouter.getInstance().build(ARouterPath.MODULE_VIEWS).navigation();
        }else if (id==R.id.module_main_util){
            ARouter.getInstance().build(ARouterPath.MODULE_UTIL).navigation();
        }
    }
}
