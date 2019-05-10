package org.gmfbilu.superapp.module_java.webSocket;

import android.os.Bundle;
import android.view.View;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_java.R;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/**
 * 利用OkHttp实现WebSocket通信？
 */
public class WebSocketFragment extends BaseFragment {

    private Toolbar mToolbar;

    public static WebSocketFragment newInstance() {
        Bundle args = new Bundle();
        WebSocketFragment fragment = new WebSocketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_java_toolbar);
    }

    @Override
    public int setLayout() {
         return R.layout.module_java_fragment_websocket;
    }

    @Override
    public void onClick(View v) {

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
        mToolbar.setTitle("WebSocket");
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
