package org.gmfbilu.superapp.module_view.fragment.module.thirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.fragment.base.BaseMainFFragment;
import org.gmfbilu.superapp.module_view.fragment.module.MainFFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class MainThirdFragment extends BaseMainFFragment {

    public static MainThirdFragment newInstance() {
        Bundle args = new Bundle();
        MainThirdFragment fragment = new MainThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.module_view_tv_tab3).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_tab_third;
    }


    /**
     * 主要代码
     *
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.module_view_tv_tab3) {
            //MainThirdFragment位于MainFragment的下一层，DetailFragment和MainThirdFragment同一层
            ((MainFFragment) getParentFragment()).start(DetailFragment.newInstance("Android 0 Develop......"));
        }
    }
}
