package org.gmfbilu.superapp.module_util.webview.normalWebView;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;


/**
 * WebView继承自AbsoluteLayout，展示网页的同时，也可以在其中放入其他的子View。
 */

/**
 * WebView的状态：
 * webView.onResume()，激活WebView为活跃状态，能正常执行网页的响应。
 * webView.onPause()，当页面被失去焦点被切换到后台不可见状态，需要执行onPause。通过onPause动作通知内核暂停所有的动作，比如DOM的解析、plugin的执行、JavaScript执行。
 * webView.pauseTimers()，当应用程序(存在webview)被切换到后台时，这个方法不仅仅针对当前的webview而是全局的全应用程序的webview。它会暂停所有webview的layout，parsing，javascripttimer。降低CPU功耗。
 * webView.resumeTimers()，恢复pauseTimers状态。
 * <p>
 * 关于前进 / 后退网页：
 * Webview.canGoBack() ，是否可以后退。
 * Webview.goBack()，后退网页。
 * Webview.canGoForward()，是否可以前进  。
 * Webview.goForward()，前进网页。
 * Webview.goBackOrForward(intsteps)，以当前的index为起始点前进或者后退到历史记录中指定的steps，如果steps为负数则为后退，正数则为前进。
 * <p>
 * Back键控制网页后退：
 * 在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身。在当前Activity中处理并消费掉该 Back 事件
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
 * mWebView.goBack();
 * return true;
 * }
 * return super.onKeyDown(keyCode, event);
 * }
 * <p>
 * 清除缓存数据：
 * Webview.clearCache(true)，清除网页访问留下的缓存，由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序。
 * Webview.clearHistory()，清除当前webview访问的历史记录，
 * Webview.clearFormData()，这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
 * <p>
 * WebSettings类：
 * WebSettings webSettings = webView.getSettings();
 * webSettings.setJavaScriptEnabled(true);  如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
 * webSettings.setPluginsEnabled(true); 支持插件
 * //设置自适应屏幕，两者合用
 * webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
 * webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
 * //缩放操作
 * webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
 * webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
 * webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
 * //其他细节操作
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
 * webSettings.setAllowFileAccess(true); //设置可以访问文件
 * webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
 * webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
 * webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
 * <p>
 * <p>
 * 设置WebView缓存：
 * 当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹，请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下。
 * /优先使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
 * //缓存模式如下：
 * //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
 * //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
 * //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 * //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 * //不使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
 * <p>
 * 结合使用（离线加载）：
 * if (NetStatusUtil.isConnected(getApplicationContext())) {
 * webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
 * } else {
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
 * }
 * webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
 * webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
 * webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
 * String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
 * webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
 * <p>
 * 关于前进 / 后退网页：
 * Webview.canGoBack() ，是否可以后退。
 * Webview.goBack()，后退网页。
 * Webview.canGoForward()，是否可以前进  。
 * Webview.goForward()，前进网页。
 * Webview.goBackOrForward(intsteps)，以当前的index为起始点前进或者后退到历史记录中指定的steps，如果steps为负数则为后退，正数则为前进。
 * <p>
 * Back键控制网页后退：
 * 在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身。在当前Activity中处理并消费掉该 Back 事件
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
 * mWebView.goBack();
 * return true;
 * }
 * return super.onKeyDown(keyCode, event);
 * }
 * <p>
 * 清除缓存数据：
 * Webview.clearCache(true)，清除网页访问留下的缓存，由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序。
 * Webview.clearHistory()，清除当前webview访问的历史记录，
 * Webview.clearFormData()，这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
 * <p>
 * WebSettings类：
 * WebSettings webSettings = webView.getSettings();
 * webSettings.setJavaScriptEnabled(true);  如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
 * webSettings.setPluginsEnabled(true); 支持插件
 * //设置自适应屏幕，两者合用
 * webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
 * webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
 * //缩放操作
 * webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
 * webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
 * webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
 * //其他细节操作
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
 * webSettings.setAllowFileAccess(true); //设置可以访问文件
 * webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
 * webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
 * webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
 * <p>
 * <p>
 * 设置WebView缓存：
 * 当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹，请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下。
 * /优先使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
 * //缓存模式如下：
 * //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
 * //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
 * //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 * //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 * //不使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
 * <p>
 * 结合使用（离线加载）：
 * if (NetStatusUtil.isConnected(getApplicationContext())) {
 * webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
 * } else {
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
 * }
 * webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
 * webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
 * webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
 * String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
 * webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
 * <p>
 * WebViewClient类：处理各种通知 & 请求事件
 * 常见方法1：shouldOverrideUrlLoading()，打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
 * //步骤1. 选择加载方式
 * //方式1. 加载一个网页：
 * webView.loadUrl("http://www.google.com/");
 * //方式2：加载apk包中的html页面
 * webView.loadUrl("file:///android_asset/test.html");
 * //方式3：加载手机本地的html页面
 * webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
 * //步骤2. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
 * webView.setWebViewClient(new WebViewClient(){
 *
 * @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
 * view.loadUrl(url);
 * return true;
 * }
 * });
 * <p>
 * 常见方法2：onPageStarted()，开始载入页面调用的
 * 常见方法3：onPageFinished()，在页面加载结束时调用
 * 常见方法4：onLoadResource()，在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
 * 常见方法5：onReceivedError()，加载页面的服务器出现错误时（如404）调用
 * 常见方法6：onReceivedSslError()，处理https请求
 * <p>
 * WebChromeClient类：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
 * 常见方法1： onProgressChanged()，获得网页的加载进度并显示
 * 常见方法2： onReceivedTitle()，获取Web页中的标题
 * <p>
 * 关于前进 / 后退网页：
 * Webview.canGoBack() ，是否可以后退。
 * Webview.goBack()，后退网页。
 * Webview.canGoForward()，是否可以前进  。
 * Webview.goForward()，前进网页。
 * Webview.goBackOrForward(intsteps)，以当前的index为起始点前进或者后退到历史记录中指定的steps，如果steps为负数则为后退，正数则为前进。
 * <p>
 * Back键控制网页后退：
 * 在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身。在当前Activity中处理并消费掉该 Back 事件
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
 * mWebView.goBack();
 * return true;
 * }
 * return super.onKeyDown(keyCode, event);
 * }
 * <p>
 * 清除缓存数据：
 * Webview.clearCache(true)，清除网页访问留下的缓存，由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序。
 * Webview.clearHistory()，清除当前webview访问的历史记录，
 * Webview.clearFormData()，这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
 * <p>
 * WebSettings类：
 * WebSettings webSettings = webView.getSettings();
 * webSettings.setJavaScriptEnabled(true);  如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
 * webSettings.setPluginsEnabled(true); 支持插件
 * //设置自适应屏幕，两者合用
 * webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
 * webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
 * //缩放操作
 * webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
 * webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
 * webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
 * //其他细节操作
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
 * webSettings.setAllowFileAccess(true); //设置可以访问文件
 * webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
 * webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
 * webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
 * <p>
 * <p>
 * 设置WebView缓存：
 * 当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹，请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下。
 * /优先使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
 * //缓存模式如下：
 * //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
 * //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
 * //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 * //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 * //不使用缓存:
 * WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
 * <p>
 * 结合使用（离线加载）：
 * if (NetStatusUtil.isConnected(getApplicationContext())) {
 * webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
 * } else {
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
 * }
 * webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
 * webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
 * webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
 * String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
 * webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
 * <p>
 * WebViewClient类：处理各种通知 & 请求事件
 * 常见方法1：shouldOverrideUrlLoading()，打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
 * //步骤1. 选择加载方式
 * //方式1. 加载一个网页：
 * webView.loadUrl("http://www.google.com/");
 * //方式2：加载apk包中的html页面
 * webView.loadUrl("file:///android_asset/test.html");
 * //方式3：加载手机本地的html页面
 * webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
 * //步骤2. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
 * webView.setWebViewClient(new WebViewClient(){
 * @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
 * view.loadUrl(url);
 * return true;
 * }
 * });
 * <p>
 * 常见方法2：onPageStarted()，开始载入页面调用的
 * 常见方法3：onPageFinished()，在页面加载结束时调用
 * 常见方法4：onLoadResource()，在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
 * 常见方法5：onReceivedError()，加载页面的服务器出现错误时（如404）调用
 * 常见方法6：onReceivedSslError()，处理https请求
 * <p>
 * WebChromeClient类：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
 * 常见方法1： onProgressChanged()，获得网页的加载进度并显示
 * 常见方法2： onReceivedTitle()，获取Web页中的标题
 */

/**
 * 关于前进 / 后退网页：
 * Webview.canGoBack() ，是否可以后退。
 * Webview.goBack()，后退网页。
 * Webview.canGoForward()，是否可以前进  。
 * Webview.goForward()，前进网页。
 * Webview.goBackOrForward(intsteps)，以当前的index为起始点前进或者后退到历史记录中指定的steps，如果steps为负数则为后退，正数则为前进。
 *
 * Back键控制网页后退：
 * 在不做任何处理前提下 ，浏览网页时点击系统的“Back”键,整个 Browser 会调用 finish()而结束自身。在当前Activity中处理并消费掉该 Back 事件
 * public boolean onKeyDown(int keyCode, KeyEvent event) {
 * 	if ((keyCode == KEYCODE_BACK) && mWebView.canGoBack()) {
 * 		mWebView.goBack();
 * 		return true;
 *        }
 * 	return super.onKeyDown(keyCode, event);
 * }
 */

/**
 * 清除缓存数据：
 * Webview.clearCache(true)，清除网页访问留下的缓存，由于内核缓存是全局的因此这个方法不仅仅针对webview而是针对整个应用程序。
 * Webview.clearHistory()，清除当前webview访问的历史记录，
 * Webview.clearFormData()，这个api仅仅清除自动完成填充的表单数据，并不会清除WebView存储到本地的数据。
 */

/**
 * WebSettings类：
 * WebSettings webSettings = webView.getSettings();
 * webSettings.setJavaScriptEnabled(true);  如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
 * webSettings.setPluginsEnabled(true); 支持插件
 * //设置自适应屏幕，两者合用
 * webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
 * webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
 * //缩放操作
 * webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
 * webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
 * webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
 * //其他细节操作
 * webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
 * webSettings.setAllowFileAccess(true); //设置可以访问文件
 * webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
 * webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
 * webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
 *
 *
 * 设置WebView缓存：
 * 当加载 html 页面时，WebView会在/data/data/包名目录下生成 database 与 cache 两个文件夹，请求的 URL记录保存在 WebViewCache.db，而 URL的内容是保存在 WebViewCache 文件夹下。
 * /优先使用缓存:
 *     WebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
 *         //缓存模式如下：
 *         //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
 *         //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
 *         //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
 *         //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
 * 	//不使用缓存:
 * 	WebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
 *
 * 	结合使用（离线加载）：
 * 	if (NetStatusUtil.isConnected(getApplicationContext())) {
 *     webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);//根据cache-control决定是否从网络上取数据。
 * } else {
 *     webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//没网，则从本地获取，即离线加载
 * }
 * webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
 * webSettings.setDatabaseEnabled(true);   //开启 database storage API 功能
 * webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
 * String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
 * webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
 */


/**
 * 如果没有WebViewClient类，那么打开就会跳转到浏览器打开网页而不是在app内
 * WebViewClient类：处理各种通知 & 请求事件
 * 常见方法1：shouldOverrideUrlLoading()，打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
 * //步骤1. 选择加载方式
 *   //方式1. 加载一个网页：
 *   webView.loadUrl("http://www.google.com/");
 *   //方式2：加载apk包中的html页面
 *   webView.loadUrl("file:///android_asset/test.html");
 *   //方式3：加载手机本地的html页面
 *    webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");
 * //步骤2. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
 *     webView.setWebViewClient(new WebViewClient(){
 *       @Override
 *       public boolean shouldOverrideUrlLoading(WebView view, String url) {
 *           view.loadUrl(url);
 *       return true;
 *       }
 *   });
 *
 * 常见方法2：onPageStarted()，开始载入页面调用的
 * 常见方法3：onPageFinished()，在页面加载结束时调用
 * 常见方法4：onLoadResource()，在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
 * 常见方法5：onReceivedError()，加载页面的服务器出现错误时（如404）调用
 * 常见方法6：onReceivedSslError()，处理https请求
 */


/**
 * WebChromeClient类：辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
 * 常见方法1： onProgressChanged()，获得网页的加载进度并显示
 * 常见方法2： onReceivedTitle()，获取Web页中的标题
 */


/**
 * 避免WebView内存泄露：
 * 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空
 * @Override
 *     protected void onDestroy() {
 *         if (mWebView != null) {
 *             mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
 *             mWebView.clearHistory();
 *
 *             ((ViewGroup) mWebView.getParent()).removeView(mWebView);
 *             mWebView.destroy();
 *             mWebView = null;
 *         }
 *         super.onDestroy();
 *     }
 */


/**
 * WebView与 JS 交互：
 * Android与JS通过WebView互相调用方法，实际上是：Android去调用JS的代码，JS去调用Android的代码。二者沟通的桥梁是WebView。
 * 对于Android调用JS代码的方法有2种：
 * 1.通过WebView的loadUrl()
 * 2.通过WebView的evaluateJavascript()
 *
 * 对于JS调用Android代码的方法有3种：
 * 1.通过WebView的addJavascriptInterface()进行对象映射
 * 2.通过WebViewClient的shouldOverrideUrlLoading()方法回调拦截url
 * 3.通过WebChromeClient的onJsAlert()、onJsConfirm()、onJsPrompt()方法回调拦截JS对话框alert()、confirm()、prompt()消息
 */
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
        // 设置允许JS弹窗
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mSettings.setDefaultTextEncodingName("UTF-8");
    }
}
