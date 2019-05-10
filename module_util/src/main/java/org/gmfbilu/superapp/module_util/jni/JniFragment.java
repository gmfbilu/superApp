package org.gmfbilu.superapp.module_util.jni;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class JniFragment extends BaseFragment {

    static {
        //这个名字和src/main/cpp/中文件名一致
        System.loadLibrary("jniDemo");
        System.loadLibrary("jniDemo2");
    }

    public native String getJniString();

    private Toolbar mToolbar;

    public static JniFragment newInstance() {
        Bundle args = new Bundle();
        JniFragment fragment = new JniFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        view.findViewById(R.id.module_util_bt_hello).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_jni;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.module_util_bt_hello) {
            Toast.makeText(_mActivity, JniDemoLib.getString()+"---"+getJniString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 所以在onActivityCreated进行一些简单的View初始化(比如 toolbar设置标题,返回按钮; 显示加载数据的进度条等),
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar.setNavigationIcon(R.mipmap.lib_base_ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressed());
        mToolbar.setTitle("JNI");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

    }

}
