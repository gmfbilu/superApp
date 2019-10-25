package org.gmfbilu.superapp.module_util.webview.x5Webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import org.gmfbilu.superapp.lib_base.base.BaseFragment;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.module_util.R;
import org.gmfbilu.superapp.module_util.webview.jsBridge.JsBridgeActivity;
import org.json.JSONException;
import org.json.JSONObject;


public class WebViewFragment extends BaseFragment {

    private Toolbar mToolbar;
    private X5WebView mWebView;
    private ProgressBar mProgressBar;
    private String mHeaderUrl;
    private StringBuilder mUrl;


    public static WebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        WebViewFragment fragment = new WebViewFragment();
        args.putString("", url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void findViewById_setOnClickListener(View view) {
        mToolbar = view.findViewById(R.id.module_util_toolbar);
        mWebView = view.findViewById(R.id.webview);
        mProgressBar = view.findViewById(R.id.progressBar);
        view.findViewById(R.id.bt_JsBridge).setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.module_util_fragment_webview;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_JsBridge) {
            startActivity(new Intent(_mActivity, JsBridgeActivity.class));
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
        mToolbar.setTitle("WebView");
    }

    /**
     * 然后在onEnterAnimationEnd()方法里进行 复杂的耗时的初始化 (比如FragmentPagerAdapter的初始化 加载数据等)
     *
     * @param savedInstanceState
     */
    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        setDefaultWebSettings();
    }

    private void setDefaultWebSettings() {
        mWebView.setWebViewClient(new X5WebViewClient(mProgressBar));
        mWebView.setWebChromeClient(new X5WebChromeClient(mProgressBar, null));
        mWebView.addJavascriptInterface(new InJavaScript(), "tgs");
        loadH5();
    }

    private void loadH5() {
        mWebView.loadUrl("http://www.sanabang.com/usercenter");
        //mWebView.loadUrl("file:///android_asset/jsbridgedemo.html");
        // mWebView.loadUrl(mHeaderUrl);
        //mWebView.loadUrl(mUrl.toString());
        //mWebView.postUrl(mHeaderUrl, EncodingUtils.getBytes(mUrl.toString(), "UTF-8"));
    }


    @SuppressLint("JavascriptInterface")
    private class InJavaScript {

        @JavascriptInterface
        private void showToast(String message) {
            LoggerUtil.d("showToast:" + message);
        }

        /**
         * 获取是安卓还是ios
         */
        @JavascriptInterface
        private String getPlatform() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("platform", "android");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoggerUtil.d("getPlatform:" + jsonObject.toString());
            return jsonObject.toString();
        }


        /**
         * 获取Token
         */
        @JavascriptInterface
        private String getToken() {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("token", "97cf2e36-78b4-4148-8f14-dd438da5e748");
                jsonObject.put("organId", "1002");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LoggerUtil.d("getToken:" + jsonObject.toString());
            return jsonObject.toString();
        }


        /**
         * 登录信息失效，请重新登录
         */
        @JavascriptInterface
        private void tokenInvalid() {
            LoggerUtil.d("tokenInvalid:");
        }


    }


    @Override
    public void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    private void destroyWebView() {
        if (mWebView != null) {
            mWebView.destroy();
        }
    }
}
