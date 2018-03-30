package org.gmfbilu.superapp.module_fragment.module.secondTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gmfbilu.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_fragment.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class CycleFragment extends BaseFragment {

    private static final String ARG_NUMBER = "arg_number";

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


    @Override
    public void findViewById_setOnClickListener(View view) {
        Toolbar   toolbar = view.findViewById(R.id.toolbar);
        view.findViewById(R.id.btn_next).setOnClickListener(this);
        view.findViewById(R.id.btn_next_with_finish).setOnClickListener(this);

        String title = "CyclerFragment " + mNumber;

        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());

    }

    @Override
    public int setLayout() {
        return R.layout.fragment_fragment_new_feature_cycle;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_next) {
            //启动下一个。CycleFragment和MainFragment同一层
            start(CycleFragment.newInstance(mNumber + 1));
        } else if (id == R.id.btn_next_with_finish) {
            //启动下一个并结束自己
            startWithPop(CycleFragment.newInstance(mNumber + 1));
        }
    }
}
