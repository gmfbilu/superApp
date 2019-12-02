package org.gmfbilu.superapp.module_util.webview.normalWebView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.module_util.R;


public class NormalWebViewFragment extends BaseFragment {

    private TextView tv_title;
    private TextView tv_close;
    private NormalWebView mWebView;
    private ProgressBar mProgressBar;

    public static NormalWebViewFragment newInstance() {
        Bundle args = new Bundle();
        NormalWebViewFragment fragment = new NormalWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        view.findViewById(R.id.iv_back).setOnClickListener(this);
        view.findViewById(R.id.bt_java_js).setOnClickListener(this);
        tv_close = view.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
        tv_title = view.findViewById(R.id.tv_title);
        mWebView = view.findViewById(R.id.webView);
        mProgressBar = view.findViewById(R.id.progressBar);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_normalwebview;
    }


    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        setDefaultWebSettings();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            back();
        } else if (id == R.id.tv_close) {
            _mActivity.onBackPressed();
        } else if (id == R.id.bt_java_js) {

        }
    }

    private void setDefaultWebSettings() {
        mWebView.setWebViewClient(new NormalWebViewClient(mProgressBar, tv_title, tv_close));
        mWebView.setWebChromeClient(new NormalWebChromeClient(mProgressBar, tv_title));
        //mWebView.loadUrl("https://www.bing.com/?mkt=zh-CN");//http://www.sanabang.com //http://www.sanabang.com/transactionRulesImg
        //mWebView.loadUrl("http://www.sanabang.com");
        mWebView.loadUrl("http://www.sanabang.com/transactionRulesImg");
    }


    @Override
    public boolean onBackPressedSupport() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onBackPressedSupport();
    }

    private void back() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();//返回上一页面
        } else {
            _mActivity.onBackPressed();
        }
    }


    @Override
    public void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    private void destroyWebView() {
        if (mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }


}
