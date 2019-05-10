package org.gmfbilu.superapp.module_view.fragment.module.secondTab;

import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

import androidx.appcompat.widget.Toolbar;

public class NewFeatureFragment extends BaseFragment {


    public static NewFeatureFragment newInstance() {
        return new NewFeatureFragment();
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        Toolbar toolbar = view.findViewById(R.id.module_view_toolbar);
        toolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        toolbar.setTitle("NewFeatures");
        view.findViewById(R.id.module_fragment_bt_start_dont_hide).setOnClickListener(this);
        view.findViewById(R.id.module_fragment_bt_start).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_new_feature;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_fragment_bt_start_dont_hide) {
            // 自定义动画启动一个Fragment，并且不隐藏自己
            //NewFeatureFragment和MainFragment位于同一层，ViewFragment也和MainFragment位于同一层
            extraTransaction()
                    .setCustomAnimations(R.anim.v_fragment_enter, 0, 0, R.anim.v_fragment_exit)
                    .startDontHideSelf(ViewFragment.newInstance());
        } else if (id == R.id.module_fragment_bt_start) {
            // 自定义动画启动一个Fragment
            //CycleFragment也和MainFragment位于同一层
            extraTransaction()
                    .setCustomAnimations(R.anim.v_fragment_enter, R.anim.v_fragment_pop_exit, R.anim.v_fragment_pop_enter, R.anim.v_fragment_exit)
                    .start(CycleFragment.newInstance(0));
        }
    }
}
