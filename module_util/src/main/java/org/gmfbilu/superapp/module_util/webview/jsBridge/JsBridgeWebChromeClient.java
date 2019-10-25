package org.gmfbilu.superapp.module_util.webview.jsBridge;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;


/**
 * 当影响浏览器的事件到来时，就会通过WebChromeClient中的方法回调通知用法
 * 还有一些小问题是WebViewClient无法解决的，比如返回按键、滚动事件监听
 * 如果用webview点链接看了很多页以后，如果不做任何处理，点击系统“Back”键，整个浏览器会调用finish()而结束自身，如果希望浏览的网页回退而不是退出浏览器，需要在当前Activity中处理并消费掉该Back事件。 覆盖Activity类的onKeyDown(int keyCode, KeyEvent event)方法。
 *
 * 监听滚动事件一般都是设置setOnScrollChangedListener，可惜的是 WebView并没有给我们提供这样的方法，但是我们可以重写WebView，覆盖里面的一个方法： protected void onScrollChanged(final int l, final int t, final int oldl,final int oldt){} 然后再对外提供一个接口
 */
public class JsBridgeWebChromeClient extends WebChromeClient {

    private ProgressBar mProgressBar;
    private TextView mTitle;

    public JsBridgeWebChromeClient(ProgressBar bar, TextView title) {
        mProgressBar=bar;
        mTitle=title;
    }

    /**
     * 当网页调用alert()来弹出alert弹出框前回调，用以拦截alert()函数
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    /**
     * 当网页调用confirm()来弹出confirm弹出框前回调，用以拦截confirm()函数
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 当网页调用prompt()来弹出prompt弹出框前回调，用以拦截prompt()函数
     */
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    /**
     * 打印 console 信息
     * 如果返回true时，就表示拦截了console输出，系统就不再通过console输出出来了，如果返回false则表示没有拦截console输出，调用系统默认处理
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    /**
     * 通知程序当前页面加载进度
     * 底层实现时，是利用handler来定时轮循当前进度的，每隔一定时间查询一次
     * 如果页面较简单，可能会直接返回100，而跳过中间的各个数据。也就是说，除了100，其它任何一个数值不是一定返回的
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress < 100) {
            mProgressBar.setProgress(newProgress);
        } else {
            mProgressBar.setProgress(100);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    /*
     * 通知页面标题变化
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        mTitle.setText(title);
    }

    /*
     * 通知当前页面网站新图标
     */
    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        super.onReceivedIcon(view, icon);
    }

    /*
     * 通知主程序图标按钮URL
     */
    @Override
    public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
        super.onReceivedTouchIconUrl(view, url, precomposed);
    }

    /*
     * 显示当前WebView，为当前WebView获取焦点。
     */
    @Override
    public void onRequestFocus(WebView view) {
        super.onRequestFocus(view);
    }

    /*
     * 通知主程序关闭WebView，并从View中移除，WebCore停止任何的进行中的加载和JS功能。
     */
    @Override
    public void onCloseWindow(WebView window) {
        super.onCloseWindow(window);
    }

    /**
     * 告诉客户端显示离开当前页面的导航提示框。如果返回true，由客户端处理确认提示框，调用合适的JsResult方法。如果返回false，则返回默认值true给javascript接受离开当前页面的导航。默认：false。JsResult设置false，当前页面取消导航提示，否则离开当前页面。
     */
    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }




    /**
     *通知主程序web内容尝试使用定位API，但是没有相关的权限。主程序需要调用调用指定的定位权限申请的回调。更多说明查看GeolocationPermissions相关API。
     */
    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
        super.onGeolocationPermissionsShowPrompt(origin, callback);
    }

    /*
     * 通知程序有定位权限请求。如果onGeolocationPermissionsShowPrompt权限申请操作被取消，则隐藏相关的UI界面。
     */
    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    /**
     *通知主程序web内容尝试申请指定资源的权限（权限没有授权或已拒绝），主程序必须调用PermissionRequest#grant(String[])或PermissionRequest#deny()。如果没有覆写该方法，默认拒绝。
     */
    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
    }


    /**
     * 通知主程序相关权限被取消。任何相关UI都应该隐藏掉。
     */
    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
    }

    /**
     * 通知主程序 执行的Js操作超时。客户端决定是否中断JavaScript继续执行。如果客户端返回true，JavaScript中断执行。如果客户端返回false，则执行继续。注意：如果继续执行，重置JavaScript超时计时器。如果Js下一次检查点仍没有结束，则再次提示。
     */
    @Override
    public boolean onJsTimeout() {
        return super.onJsTimeout();
    }

    /**
     *当停止播放，Video显示为一张图片。默认图片可以通过HTML的Video的poster属性标签来指定。如果poster属性不存在，则使用默认的poster。该方法允许ChromeClient提供默认图片。
     */
    @Nullable
    @Override
    public Bitmap getDefaultVideoPoster() {
        return super.getDefaultVideoPoster();
    }

    /**
     * 当用户重放视频，在渲染第一帧前需要花费时间去缓冲足够的数据。在缓冲期间，ChromeClient可以提供一个显示的View。如：可以显示一个加载动画。
     */
    @Nullable
    @Override
    public View getVideoLoadingProgressView() {
        return super.getVideoLoadingProgressView();
    }

    /**
     * 获取访问历史Item，用于链接颜色。
     */
    @Override
    public void getVisitedHistory(ValueCallback<String[]> callback) {
        super.getVisitedHistory(callback);
    }

    /**
     * 通知客户端显示文件选择器。用来处理file类型的HTML标签，响应用户点击选择文件的按钮操作。调用filePathCallback.onReceiveValue(null)并返回true取消请求操作。
     * FileChooserParams参数的枚举列表：
     MODE_OPEN 打开
     MODE_OPEN_MULTIPLE 选中多个文件打开
     MODE_OPEN_FOLDER 打开文件夹（暂不支持）
     MODE_SAVE 保存
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

}
