package debug;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.View;
=======
>>>>>>> 2bd8b3c12b88630e0ea7404b4c2a13b77da819a3

import org.gmfbilu.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_view.MainActivity;


/**
 * Created by gmfbilu on 2018/3/7.
 * <p>
 * 组件开发模式下，用于传递数据的启动Activity，集成模式下无效
 */

public class LauncherActivity extends BaseActivity {

    @Override
<<<<<<< HEAD
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
=======
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
>>>>>>> 2bd8b3c12b88630e0ea7404b4c2a13b77da819a3
        //在这里传值给需要调试的Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
<<<<<<< HEAD

    @Override
    public int setLayout() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }
=======
>>>>>>> 2bd8b3c12b88630e0ea7404b4c2a13b77da819a3
}
