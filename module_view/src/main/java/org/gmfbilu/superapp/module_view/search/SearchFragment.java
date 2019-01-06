package org.gmfbilu.superapp.module_view.search;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.Utils;
import org.gmfbilu.superapp.module_view.R;

public class SearchFragment extends BaseFragment {

    private TextView tv_goods;
    private TextView tv_shops;
    private TextView tv_local;
    private ImageView iv_bottom_line;
    private Fragment[] mFragments;
    private int lastIndex = 0;
    private int screenWidth;


    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        tv_goods = view.findViewById(R.id.tv_goods);
        tv_goods.setOnClickListener(this);
        tv_shops = view.findViewById(R.id.tv_shops);
        tv_shops.setOnClickListener(this);
        tv_local = view.findViewById(R.id.tv_local);
        tv_local.setOnClickListener(this);
        iv_bottom_line = view.findViewById(R.id.iv_bottom_line);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_search;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_goods) {
            setIndexSelected(0);
        } else if (id == R.id.tv_shops) {
            setIndexSelected(1);
        } else if (id == R.id.tv_local) {
            setIndexSelected(2);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        screenWidth = Utils.getScreenWidth(_mActivity);
        initFragment();
    }

    private void initFragment() {
        GoodsFragment goodsFragment = GoodsFragment.newInstance();
        ShopsFragment shopsFragment = ShopsFragment.newInstance();
        LocalFragment localFragment = LocalFragment.newInstance();
        mFragments = new Fragment[]{goodsFragment, shopsFragment, localFragment};
        FragmentTransaction ft = _mActivity.getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fl_content, goodsFragment).commit();
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if (index == lastIndex) {
            return;
        }
        tv_goods.setTextColor(index == 0 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_shops.setTextColor(index == 1 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        tv_local.setTextColor(index == 2 ? Color.parseColor("#E9372F") : Color.parseColor("#333333"));
        float curTranslationX = iv_bottom_line.getTranslationX();//初始坐标，每次移动屏幕宽度/3
        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_line, "translationX", curTranslationX, index * screenWidth / 3);//绝对坐标
        animator.setDuration(300);
        animator.start();
        FragmentManager fragmentManager = _mActivity.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(mFragments[lastIndex]);
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        lastIndex = index;
    }


}
