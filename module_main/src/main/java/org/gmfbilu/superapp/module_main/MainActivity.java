package org.gmfbilu.superapp.module_main;

import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.leon.channel.helper.ChannelReaderUtil;

import org.gmfbilu.lib_base.ARouterPath;
import org.gmfbilu.lib_base.base.BaseActivity;
import org.gmfbilu.lib_base.bean.TestBean;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        String channel = ChannelReaderUtil.getChannel(getApplicationContext());
        Toast.makeText(this,channel+"好爱好爱你",Toast.LENGTH_LONG).show();
        findViewById(R.id.main_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 无参数的跳转
                 ARouter.getInstance().build(ARouterPath.MODULE_TEST).navigation();
                 */

                /**
                 * 有参数的跳转
                 ARouter.getInstance().build(ARouterPath.MODULE_TEST)
                 .withLong("key1", 100)
                 .withParcelable("key2", new TestBean(10,"jack"))
                 .navigation();
                 */

                /**
                 * 加转场动画跳转 api>16
                 */
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
                ARouter.getInstance().build(ARouterPath.MODULE_TEST)
                        .withLong("key1", 100)
                        .withParcelable("key2", new TestBean(10, "jack"))
                        .withOptionsCompat(compat)
                        .navigation();



            }
        });

        findViewById(R.id.main_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ARouterPath.MODULE_FRAGMENT).navigation();
            }
        });
    }

}
