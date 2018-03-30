package org.gmfbilu.superapp.module_fragment.module;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.gmfbilu.lib_base.ARouterPath;
import org.gmfbilu.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.module_fragment.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 主界面Activity
 *
 *
 * 启动Fragment
 * 1.如果在Activity使用，则本质是 activity.getSupportFragmentManager().getTopFragment().start(f)
 * 2.启动新的Fragment，启动者和被启动者是在同一个栈的 start(SupportFragment fragment)
 * 3.以某种启动模式，启动新的Fragment start(SupportFragment fragment, int launchMode)
 * 4.启动新的Fragment，并能接收到新Fragment的数据返回 startForResult(SupportFragment fragment,int requestCode)
 * 5.启动目标Fragment，并关闭当前Fragment startWithPop(SupportFragment fragment)
 * 6.启动目标Fragment，并关闭当前Fragment startWithPop(SupportFragment fragment)
 *
 * 出栈
 * 1.出栈当前Fragment(在当前Fragment所在栈内pop) pop()
 * 2.出栈targetFragment之上的所有Fragments popTo(Class targetFragment, boolean includeTargetFragment)
 *
 * 查找Fragment
 * 1.获取所在栈内的栈顶Fragment getTopFragment()
 * 2.获取当前Fragment所在栈内的前一个Fragment getPreFragment()
 * 3.通过class获取所在栈内的某个Fragment findFragment(Class fragmentClass)
 *
 * 输入法相关
 * 因为Fragment被出栈时，不会自动隐藏软键盘，以及弹出软键盘有些麻烦，故提供下面2个方法
 * 1.隐藏软键盘 一般用在hide时 hideSoftInput()
 * 2.显示软键盘，调用该方法后，会在onPause时自动隐藏软键盘 showSoftInput(View view)
 */

@Route(path = ARouterPath.MODULE_FRAGMENT)
public class MainActivity extends BaseActivity {


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        //loadRootX()系列方法，操作的对象是 孩子Fragment，为避免被强杀重启后重复load，建议在findChildFragment(ChildFragment.class)==null情况下才load
        //startX()，popX()，find/getX()系列方法，操作的对象是 兄弟Fragment
        //popChildX()，find/getChildX()系列方法，操作的对象是 孩子Fragment
        if (findFragment(MainFragment.class) == null) {
            loadRootFragment(R.id.fl_container, MainFragment.newInstance());
        }
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_activity_main;
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
