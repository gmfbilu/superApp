package debug;

import android.content.Intent;
import android.os.Bundle;

import org.gmfbilu.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_view.MainActivity;


/**
 * Created by gmfbilu on 2018/3/7.
 * <p>
 * 组件开发模式下，用于传递数据的启动Activity，集成模式下无效
 */

public class LauncherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在这里传值给需要调试的Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}