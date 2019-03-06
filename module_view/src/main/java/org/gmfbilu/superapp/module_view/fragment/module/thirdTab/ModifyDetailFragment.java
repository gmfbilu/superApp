package org.gmfbilu.superapp.module_view.fragment.module.thirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;
import org.gmfbilu.superapp.module_view.fragment.module.secondTab.CycleFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class ModifyDetailFragment extends BaseFragment {
    private static final String ARG_TITLE = "arg_title";

    private Toolbar mToolbar;
    private EditText mEtModiyTitle;

    private String mTitle;

    public static ModifyDetailFragment newInstance(String title) {
        Bundle args = new Bundle();
        ModifyDetailFragment fragment = new ModifyDetailFragment();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString(ARG_TITLE);
        }
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_view_toolbar);
        mEtModiyTitle = view.findViewById(R.id.module_fragment_et_modify_title);
        view.findViewById(R.id.module_fragment_bt_modify).setOnClickListener(this);
        view.findViewById(R.id.module_fragment_bt_next).setOnClickListener(this);
        mToolbar.setTitle("测试startForResult");
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mEtModiyTitle.setText(mTitle);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_tab_third_modify;
    }


    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_fragment_bt_modify) {
            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.KEY_RESULT_TITLE, mEtModiyTitle.getText().toString());
            setFragmentResult(RESULT_OK, bundle);
        } else if (id == R.id.module_fragment_bt_next) {
            start(CycleFragment.newInstance(1));
        }
    }
}
