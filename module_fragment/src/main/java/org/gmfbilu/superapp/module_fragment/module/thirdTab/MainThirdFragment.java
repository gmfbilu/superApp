package org.gmfbilu.superapp.module_fragment.module.thirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.module.MainFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class MainThirdFragment extends SupportFragment implements View.OnClickListener{

    public static MainThirdFragment newInstance() {
        Bundle args = new Bundle();
        MainThirdFragment fragment = new MainThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_third, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void initView(View view) {
        view.findViewById(R.id.fragment_tv_tab3).setOnClickListener(this);
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
        if (i == R.id.fragment_tv_tab3) {
            ((MainFragment) getParentFragment()).startBrotherFragment(DetailFragment.newInstance("Android 0 Develop......"));

        }
    }
}
