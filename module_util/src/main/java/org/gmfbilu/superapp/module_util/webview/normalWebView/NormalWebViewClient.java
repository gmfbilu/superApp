package org.gmfbilu.superapp.module_util.webview.normalWebView;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

import androidx.annotation.Nullable;


/**
 * 在影响View的事件到来时，会通过WebViewClient中的方法回调通知用户
 */
public class NormalWebViewClient extends WebViewClient {

    private ProgressBar mProgressBar;
    private TextView mTitle;
    private TextView mClose;
    private boolean isAppOpen;//是否在应用内打开网页

    public NormalWebViewClient(ProgressBar bar, TextView title, TextView close) {
        this(bar, title, close, true);
    }

    public NormalWebViewClient(ProgressBar bar, TextView title, TextView close, boolean isAppOpen) {
        mProgressBar = bar;
        mTitle = title;
        this.mClose = close;
        this.isAppOpen = isAppOpen;
    }


    /**
     * 在开始加载网页时会回调
     * 通知主程序页面当前开始加载
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mProgressBar != null) mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * 在结束加载网页时会回调
     * 通知主程序页面加载结束
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mProgressBar != null) mProgressBar.setVisibility(View.GONE);
        String title = view.getTitle();
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
        mClose.setVisibility(view.canGoBack() ? View.VISIBLE : View.GONE);
    }

    /**
     * 拦截 url 跳转,在里边添加点击链接跳转或者操作
     * 返回值是boolean类型，表示是否屏蔽WebView继续加载URL的默认行为。因为这个函数是WebView加载URL前回调的，所以如果我们return true，则WebView接下来就不会再加载这个URL了。如果我们return false，则系统就认为上层没有做处理，接下来还是会继续加载这个URL的。WebViewClient默认就是return false的
     * 只关心我们关心的拦截内容，对于不拦截的内容，让系统自己来处理即可
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
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

    /**
     * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        view.loadUrl("file:///android_asset/404.html");
    }

    /**
     * 当接收到https错误时，会回调此函数，在其中可以做错误处理
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        handler.proceed();//接受证书
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调
     * 在每一次请求资源时，都会通过这个函数来回调，比如超链接、JS文件、CSS文件、图片等，也就是说浏览器中每一次请求资源时，都会回调回来，无论任何资源！但是必须注意的是shouldInterceptRequest函数是在非UI线程中执行的，在其中不能直接做UI操作，如果需要做UI操作，则需要利用Handler来实现
     */
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    /**
     * 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * (WebView发生改变时调用)
     * 可以参考http://www.it1352.com/191180.html的用法
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }


    /**
     * 重写此方法才能够处理在浏览器中的按键事件。
     * 是否让主程序同步处理Key Event事件，如过滤菜单快捷键的Key Event事件。
     * 如果返回true，WebView不会处理Key Event，
     * 如果返回false，Key Event总是由WebView处理。默认：false
     */
    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return super.shouldOverrideKeyEvent(view, event);
    }

    /**
     * 是否重发POST请求数据，默认不重发。
     */
    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    /**
     * 更新访问历史
     */
    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    /**
     * 通知主程序输入事件不是由WebView调用。是否让主程序处理WebView未处理的Input Event。
     * 除了系统按键，WebView总是消耗掉输入事件或shouldOverrideKeyEvent返回true。
     * 该方法由event 分发异步调用。注意：如果事件为MotionEvent，则事件的生命周期只存在方法调用过程中，
     * 如果WebViewClient想要使用这个Event，则需要复制Event对象。
     */
    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    /**
     * 通知主程序执行了自动登录请求。
     */
    @Override
    public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    /**
     * 通知主程序：WebView接收HTTP认证请求，主程序可以使用HttpAuthHandler为请求设置WebView响应。默认取消请求。
     */
    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    /**
     * 通知主程序处理SSL客户端认证请求。如果需要提供密钥，主程序负责显示UI界面。
     * 有三个响应方法：proceed(), cancel() 和 ignore()。
     * 如果调用proceed()和cancel()，webview将会记住response，
     * 对相同的host和port地址不再调用onReceivedClientCertRequest方法。
     * 如果调用ignore()方法，webview则不会记住response。该方法在UI线程中执行，
     * 在回调期间，连接被挂起。默认cancel()，即无客户端认证
     */
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }
}
