package org.gmfbilu.superapp.module_view;

<<<<<<< HEAD
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.lib_base.ARouterPath;
import org.gmfbilu.lib_base.base.BaseActivity;

@Route(path = ARouterPath.MODULE_VIEWS)
public class MainActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.view_activity_main;
    }

    @Override
    public void onClick(View v) {

=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_main);
>>>>>>> 2bd8b3c12b88630e0ea7404b4c2a13b77da819a3
    }
}
