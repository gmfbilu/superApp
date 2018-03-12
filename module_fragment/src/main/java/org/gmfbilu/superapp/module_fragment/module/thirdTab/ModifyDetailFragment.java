package org.gmfbilu.superapp.module_fragment.module.thirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.gmfbilu.superapp.module_fragment.R;
import org.gmfbilu.superapp.module_fragment.module.secondTab.CycleFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class ModifyDetailFragment extends SupportFragment {
    private static final String ARG_TITLE = "arg_title";

    private Toolbar mToolbar;
    private EditText mEtModiyTitle;
    private Button mBtnModify, mBtnNext;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_tab_third_modify, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mToolbar = view.findViewById(R.id.toolbar);
        mEtModiyTitle = view.findViewById(R.id.et_modify_title);
        mBtnModify = view.findViewById(R.id.btn_modify);
        mBtnNext = view.findViewById(R.id.btn_next);
        mToolbar.setTitle("测试startForResult");
        initToolbarNav();
        mEtModiyTitle.setText(mTitle);
        mBtnModify.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.KEY_RESULT_TITLE, mEtModiyTitle.getText().toString());
            setFragmentResult(RESULT_OK, bundle);
            Toast.makeText(_mActivity, "修改成功!", Toast.LENGTH_SHORT).show();
        });
        mBtnNext.setOnClickListener(v -> start(CycleFragment.newInstance(1)));
    }

    private void initToolbarNav() {
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        hideSoftInput();
    }
}
