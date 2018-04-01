package org.gmfbilu.superapp.lib_base.base;

import android.os.Bundle;
import android.view.View;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by gmfbilu on 2018/3/7.
 */

public abstract class BaseActivity extends SupportActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = setLayout();
        if (layout != 0)
            setContentView(layout);
        findViewById_setOnClickListener(savedInstanceState);
    }

    public abstract void findViewById_setOnClickListener(Bundle savedInstanceState);

    public abstract int setLayout();

}
