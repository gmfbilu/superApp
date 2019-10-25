package org.gmfbilu.superapp.module_util.webview.x5Webview;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class X5WebChromeClient extends WebChromeClient {

    private ProgressBar mBar;
    private View mLoadingView;
    private ActionBar mActionBar;

    public X5WebChromeClient(ProgressBar bar, View loadingView) {
        this(bar, loadingView, null);
    }

    public X5WebChromeClient(ProgressBar bar, View loadingView, ActionBar actionBar) {
        mBar = bar;
        mLoadingView = loadingView;
        mActionBar = actionBar;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mBar != null) {
            if (newProgress < 100) {
                mBar.setProgress(newProgress);
            } else {
                mBar.setProgress(100);
                mBar.setVisibility(View.GONE);
            }
            if (mLoadingView != null)
                mLoadingView.setVisibility(View.GONE);
        }

    }

    // For Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        Log.i("test", "openFileChooser 1");
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
        Log.i("test", "openFileChooser 2");
    }

    // For Android  > 4.1.1
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        Log.e("test", "openFileChooser 3");
    }

    // For Android  >= 5.0
    public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     WebChromeClient.FileChooserParams fileChooserParams) {
        Log.e("test", "openFileChooser 4:" + filePathCallback.toString());
        return true;
    }



}
