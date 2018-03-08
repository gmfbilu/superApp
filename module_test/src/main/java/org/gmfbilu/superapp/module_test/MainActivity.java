package org.gmfbilu.superapp.module_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.lib_base.ARouterPath;

@Route(path = ARouterPath.MODULE_TEST)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_main);
        long key1 = getIntent().getLongExtra("key1", 0);
        Toast.makeText(this,key1+"",Toast.LENGTH_SHORT).show();
    }
}
