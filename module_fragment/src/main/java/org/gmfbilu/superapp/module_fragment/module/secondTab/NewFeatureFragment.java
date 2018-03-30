package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_fragment.R;

public class NewFeatureFragment extends BaseFragment {


    public static NewFeatureFragment newInstance() {
        return new NewFeatureFragment();
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        toolbar.setTitle("NewFeatures");
        view.findViewById(R.id.btn_start_dont_hide).setOnClickListener(this);
        view.findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_new_feature;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_start_dont_hide) {
            // 自定义动画启动一个Fragment，并且不隐藏自己
            //NewFeatureFragment和MainFragment位于同一层，ViewFragment也和MainFragment位于同一层
            extraTransaction()
                    .setCustomAnimations(R.anim.v_fragment_enter, 0, 0, R.anim.v_fragment_exit)
                    .startDontHideSelf(ViewFragment.newInstance());
        } else if (id == R.id.btn_start) {
            // 自定义动画启动一个Fragment
            //CycleFragment也和MainFragment位于同一层
            extraTransaction()
                    .setCustomAnimations(R.anim.v_fragment_enter, R.anim.v_fragment_pop_exit, R.anim.v_fragment_pop_enter, R.anim.v_fragment_exit)
                    .start(CycleFragment.newInstance(0));
        }
    }
}
