package org.gmfbilu.superapp.module_util.webview.normalWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class NormalWebView extends WebView {

    public NormalWebView(Context context) {
        this(context, null);
    }

    public NormalWebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NormalWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }


    private void init() {

        WebSettings mSettings = getSettings();
        requestFocusFromTouch();
        setClickable(true);
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mSettings.setSupportZoom(true);//是否可以缩放，默认true
        mSettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        mSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        mSettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题

        mSettings.setDisplayZoomControls(false);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.supportMultipleWindows();
        mSettings.setSupportMultipleWindows(true);
        mSettings.setDomStorageEnabled(true);
        mSettings.setDatabaseEnabled(true);
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mSettings.setAppCacheEnabled(true);
        mSettings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());
        mSettings.setAllowFileAccess(true);
        mSettings.setAllowContentAccess(true);
        mSettings.setNeedInitialFocus(true);
        mSettings.setLoadsImagesAutomatically(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        mSettings.setNeedInitialFocus(true);
        mSettings.setBlockNetworkImage(false);
        mSettings.setBlockNetworkLoads(false);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mSettings.setDefaultTextEncodingName("UTF-8");
    }
}
