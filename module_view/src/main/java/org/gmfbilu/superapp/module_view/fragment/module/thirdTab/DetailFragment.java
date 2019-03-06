package org.gmfbilu.superapp.module_view.fragment.module.thirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_view.R;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class DetailFragment extends BaseFragment {

    public static final String TAG = DetailFragment.class.getSimpleName();
    private static final int REQ_MODIFY_FRAGMENT = 100;

    private static final String ARG_TITLE = "arg_title";
    static final String KEY_RESULT_TITLE = "title";

    private Toolbar mToolbar;
    private TextView mTvContent;
    private String mTitle;

    public static DetailFragment newInstance(String title) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(ARG_TITLE);
        }
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_view_toolbar);
        view.findViewById(R.id.module_view_fab).setOnClickListener(this);
        mTvContent = view.findViewById(R.id.module_view_tv_content);
        mToolbar.setTitle(mTitle);
    }

    @Override
    public int setLayout() {
        return R.layout.module_view_fragment_tab_third_detail;
    }


    /**
     * 这里演示:
     * 比较复杂的Fragment页面会在第一次start时,导致动画卡顿
     * Fragmentation提供了onEnterAnimationEnd()方法,该方法会在 入栈动画 结束时回调
     * 所以在onCreateView进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        mTvContent.setText(R.string.module_view_large_text);
    }


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_MODIFY_FRAGMENT && resultCode == RESULT_OK && data != null) {
            mTitle = data.getString(KEY_RESULT_TITLE);
            mToolbar.setTitle(mTitle);
            // 保存被改变的 title
            getArguments().putString(ARG_TITLE, mTitle);
            Toast.makeText(_mActivity, "修改标题成功！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_view_fab) {
            //ModifyDetailFragment和DetailFragment位于同一层
            startForResult(ModifyDetailFragment.newInstance(mTitle), REQ_MODIFY_FRAGMENT);
        }
    }
}
