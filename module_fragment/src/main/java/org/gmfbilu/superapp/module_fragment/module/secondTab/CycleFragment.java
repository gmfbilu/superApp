package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.gmfbilu.superapp.module_fragment.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class CycleFragment extends SupportFragment {

    private static final String ARG_NUMBER = "arg_number";

    private Toolbar mToolbar;
    private Button mBtnNext, mBtnNextWithFinish;

    private int mNumber;

    public static CycleFragment newInstance(int num) {
        Bundle bundle = new Bundle();
        CycleFragment cycleFragment = new CycleFragment();
        bundle.putInt(ARG_NUMBER, num);
        cycleFragment.setArguments(bundle);
        return cycleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mNumber = args.getInt(ARG_NUMBER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_new_feature_cycle, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mBtnNext = view.findViewById(R.id.btn_next);
        mBtnNextWithFinish = view.findViewById(R.id.btn_next_with_finish);

        String title = "CyclerFragment " + mNumber;

        mToolbar.setTitle(title);
        initToolbarNav();

        mBtnNext.setOnClickListener(v -> {
            //启动下一个
            start(CycleFragment.newInstance(mNumber + 1));
        });
        mBtnNextWithFinish.setOnClickListener(v -> {
            //启动下一个并结束自己
            startWithPop(CycleFragment.newInstance(mNumber + 1));
        });
    }

    private void initToolbarNav() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
    }
}
