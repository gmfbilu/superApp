package org.gmfbilu.superapp.module_util.webview.jsBridge;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

import org.gmfbilu.superapp.lib_base.base.BaseActivity;
import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.ToastUtil;
import org.gmfbilu.superapp.module_util.R;
import org.json.JSONException;
import org.json.JSONObject;

public class JsBridgeActivity extends BaseActivity {

    private TextView tv_title;
    private TextView tv_close;
    private BridgeWebView bridgeWebView;
    private ProgressBar mProgressBar;


    @Override
    public void findViewById_setOnClickListener(Bundle savedInstanceState) {
        findViewById(R.id.iv_back).setOnClickListener(this);
        tv_close = findViewById(R.id.tv_close);
        tv_close.setOnClickListener(this);
        findViewById(R.id.bt_java_js).setOnClickListener(this);
        tv_title = findViewById(R.id.tv_title);
        bridgeWebView = findViewById(R.id.webView);
        mProgressBar = findViewById(R.id.progressBar);
        initWebView();

    }

    @Override
    public int setLayout() {
        return R.layout.module_util_activity_webview_jsbridge;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            back();
        } else if (id == R.id.tv_close) {
            finish();
        } else if (id == R.id.bt_java_js) {
            /**
             * java调用js的方法，
             * handlerName和js中的bridge.registerHandler("JavaJs", function(data, responseCallback)一致
             * data就是Java传递给js中的参数
             * onCallBack就是js返回给Java的参数
             */
            bridgeWebView.callHandler("JavaJs",
                    "Java调用js方法并把本数据传递给js,下面是js回调给Java的数据",
                    new CallBackFunction() {

                        @Override
                        public void onCallBack(String data) {
                            //js返回的数据
                            LoggerUtil.d(data);
                        }

                    });
        }
    }

    private void initWebView() {
        bridgeWebView.setWebViewClient(new JsBridgeWebViewClient(mProgressBar, tv_title, tv_close));
        bridgeWebView.setWebChromeClient(new JsBridgeWebChromeClient(mProgressBar, tv_title));


        //简单的没有回调的调用方式
        bridgeWebView.setDefaultHandler(new DefaultHandler());


        /**
         * Js调用java的方式也可以简化为：
         */
        bridgeWebView.send("hello haha", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                //data就是connectWebViewJavascriptBridge
                LoggerUtil.d(data);
            }
        });

        /**
         * js调用Java方法
         * handName就是window.WebViewJavascriptBridge.callHandler('submitFromWeb', {'param': '中文测试'}, , function(responseData) {}}
         */
        bridgeWebView.registerHandler("JsJava", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                //data就是window.WebViewJavascriptBridge.callHandler('submitFromWeb', {'param': '中文测试'},中的{'param': '中文测试'}
                LoggerUtil.d(data);
                function.onCallBack("这是要发送给js中的数据");
            }

        });


        /**
         * 将Java对象作为数据发送给js
         * Js如果需要调用Java提供的方法时候，则需要调用这个Handler，而在注册时参数functionInJs将作为Js调用时使用的Key值
         */

        JsBridgeBean user = new JsBridgeBean();
        JsBridgeBean.Location location = new JsBridgeBean.Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";
        bridgeWebView.callHandler("JsJava", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                //将Java对象作为数据发送给js后，js返回的数据
                //这里打印的应该是上面Handler实现方法中的callback的入参，"submitFromWeb exe, response data 中文 from Java"
                LoggerUtil.d(data);
            }
        });


        bridgeWebView.addJavascriptInterface(new InJavaScript(), "tgs");

        //加载url
        //bridgeWebView.loadUrl("file:///android_asset/jsbridgedemo.html");
        //bridgeWebView.loadUrl("http://www.sanabang.com/usercenter");
        bridgeWebView.loadUrl("https://www.bing.com/?mkt=zh-CN");
    }


    private class InJavaScript {

        @JavascriptInterface
        public void showToast(String message) {
            //子线程吗
            ToastUtil.show(message);
        }

        /**
         * 获取是安卓还是ios
         */
        @JavascriptInterface
        public String getPlatform() {
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
        public String getToken() {
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
        public void tokenInvalid() {
            LoggerUtil.d("tokenInvalid:");
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //改写物理返回键的逻辑
        if (keyCode == KeyEvent.KEYCODE_BACK && bridgeWebView.canGoBack()) {
            if (bridgeWebView.copyBackForwardList().getSize() <= 2) {
                finish();
            } else {
                bridgeWebView.goBack();
            }
            return true;
        } else {
            onBackPressedSupport();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void back() {
        if (bridgeWebView.canGoBack()) {
            bridgeWebView.goBack();//返回上一页面
        } else {
            finish();
        }
    }


    @Override
    public void onDestroy() {
        if (bridgeWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = bridgeWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(bridgeWebView);
            }

            bridgeWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            bridgeWebView.getSettings().setJavaScriptEnabled(false);
            bridgeWebView.clearHistory();
            bridgeWebView.clearView();
            bridgeWebView.removeAllViews();
            bridgeWebView.destroy();
        }
        super.onDestroy();
    }

}
