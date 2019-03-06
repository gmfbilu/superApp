package org.gmfbilu.superapp.module_view.fragment.module;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_view.MainFragment;
import org.gmfbilu.superapp.module_view.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class FragmentActivity extends BaseActivity {

    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        //loadRootX()系列方法，操作的对象是 孩子Fragment，为避免被强杀重启后重复load，建议在findChildFragment(ChildFragment.class)==null情况下才load
        //startX()，popX()，find/getX()系列方法，操作的对象是 兄弟Fragment
        //popChildX()，find/getChildX()系列方法，操作的对象是 孩子Fragment
        if (findFragment(MainFFragment.class) == null) {
            loadRootFragment(R.id.lib_base__fl_container, MainFFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.module_lib_base_fl_container;
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onClick(View v) {

    }
}
