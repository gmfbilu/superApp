package org.gmfbilu.lib_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 2018/3/19.
 */

public abstract class BaseFragment extends SupportFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayout(), container, false);
        findViewById_setOnClickListener(view);
        return view;
    }

    /**
     * 初始化方法
     * @param view
     */
    public abstract void findViewById_setOnClickListener(View view);

    public abstract int setLayout();

}
