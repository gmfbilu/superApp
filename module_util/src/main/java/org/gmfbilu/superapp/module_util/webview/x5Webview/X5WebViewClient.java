package org.gmfbilu.superapp.module_util.webview.x5Webview;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;


public class X5WebViewClient extends WebViewClient {

    private ProgressBar mBar;
    private boolean isAppOpen;//是否在应用内打开网页

    public X5WebViewClient(ProgressBar bar) {
        this(bar, true);
    }

    public X5WebViewClient(ProgressBar bar, boolean isAppOpen) {
        mBar = bar;
        this.isAppOpen = isAppOpen;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LoggerUtil.d(url);
        if (url != null && url.startsWith("orpheus")) {
            return true;
        }
        if (url != null && url.startsWith("http")) {
            if (!isAppOpen) {
                return false;
            } else {
                view.loadUrl(url);
            }
        }
        return true;
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        LoggerUtil.d(url);
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        LoggerUtil.d(url);
        if (mBar != null) mBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LoggerUtil.d(url);
        if (mBar != null) mBar.setVisibility(View.GONE);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        view.loadUrl("file:///android_asset/404.html");
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();//接受证书
    }
}
