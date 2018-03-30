package org.gmfbilu.superapp.module_fragment.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.lib_base.base.eventbusactivityscope.EventBusActivityScope;
import org.gmfbilu.lib_base.base.eventbusactivityscope.event.TabSelectedEvent;
import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.module.firstTab.MainFirstFragment;
import org.gmfbilu.superapp.module_fragment.module.secondTab.MainSecondFragment;
import org.gmfbilu.superapp.module_fragment.module.thirdTab.MainThirdFragment;
import org.gmfbilu.superapp.module_fragment.views.BottomBar;
import org.gmfbilu.superapp.module_fragment.views.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 * 主界面Fragment也是根fragment
 * onSupportVisible()等生命周期调用顺序规范化：onActivityCreated() -> onResume() -> onSupportVisible -> onLazyInitView() => onSupportInvisible() -> onPause()
 * 比如MainFragment内有个子MainFirstFragment，MainFirstFragment通过MainFragment startFragment(OneLayerFragment) （此时MainFragment和OneLayerFragment是同级Fragment），当你pop(OneLayerFragment)时，MainFragment和MainFirstFragment的onSupportVisible()都会被回调
 */


public class MainFragment extends BaseFragment {

    private static final int REQ_MSG = 10;

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private BottomBar mBottomBar;


    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mBottomBar = view.findViewById(R.id.bottomBar);
        mBottomBar
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_message_white_24dp, "消息"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_account_circle_white_24dp, "发现"))
                .addItem(new BottomBarTab(_mActivity, R.mipmap.ic_discover_white_24dp, "更多"));

        // 模拟未读消息
        mBottomBar.getItem(FIRST).setUnreadCount(9);
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                // show一个Fragment，hide一个Fragment； 主要用于类似微信主页那种 切换tab的情况
                showHideFragment(mFragments[position], mFragments[prePosition]);
                BottomBarTab tab = mBottomBar.getItem(FIRST);
                if (position == FIRST) {
                    tab.setUnreadCount(0);
                } else {
                    tab.setUnreadCount(tab.getUnreadCount() + 1);
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                //EventBus发出消息，可以用RxBus替代，发出消息不用订阅也不用取消订阅，而接收消息需要订阅和取消订阅
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_main;
    }


    /**
     * 此方法为主要操作代码区域
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //装载根Fragment，一般在findChildFragment(ChildFragment.class)==null时load
        SupportFragment firstFragment = findChildFragment(MainFirstFragment.class);
        if (firstFragment == null) {
            mFragments[FIRST] = MainFirstFragment.newInstance();
            mFragments[SECOND] = MainSecondFragment.newInstance();
            mFragments[THIRD] = MainThirdFragment.newInstance();
            // 装载多个根Fragment，用于同级Fragment的场景
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findChildFragment(MainSecondFragment.class);
            mFragments[THIRD] = findChildFragment(MainThirdFragment.class);
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MSG && resultCode == RESULT_OK) {

        }
    }


    @Override
    public void onClick(View v) {

    }
}
