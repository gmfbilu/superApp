package org.gmfbilu.superapp.lib_base.http;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.base.BaseApplication;
import org.gmfbilu.superapp.lib_base.bean.request.GetDictionaryDatReq;
import org.gmfbilu.superapp.lib_base.bean.request.GetProductsByTypeReq;
import org.gmfbilu.superapp.lib_base.bean.request.LoginReq;
import org.gmfbilu.superapp.lib_base.bean.response.AddJJMergeRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetDictionaryDatRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetProductsByTypeRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetSaleManListRes;
import org.gmfbilu.superapp.lib_base.bean.response.LoginRes;
import org.gmfbilu.superapp.lib_base.utils.AppUtils;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class HttpMethods {

    private ApiService mApiService;
    private static HttpMethods INSTANCE;


    public static HttpMethods getInstance() {
        synchronized (HttpMethods.class) {
            if (INSTANCE == null) {
                INSTANCE = new HttpMethods();
            }
        }
        return INSTANCE;
    }

    /**
     * 由于是单例模式，HttpMethods会一直和程序生命周期保持一致。在配置需要改变的情况下需要结束实例重写创建
     */
    public static void clearSingleton() {
        INSTANCE = null;
    }


    // 云端响应头拦截器，用来配置缓存策略
    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 0秒内直接读缓存
    private static final long CACHE_AGE_SEC = 0;
    // 在这里统一配置请求头缓存策略以及响应头缓存策略
    private Interceptor mRewriteCacheControlInterceptor = chain -> {
        Request request = chain.request();
        if (AppUtils.isNetworkConnected()) {
            // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
            request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            Response response = chain.proceed(request);
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
        } else {
            // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
            request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            Response response = chain.proceed(request);
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
        }

    };

    private HttpMethods() {
        initSetting();
    }

    private void initSetting() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        //日志打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                if (Constant.ISSHOWLOG) {
                    Log.d(Constant.LOG_NAME, message);
                }
            }
        });
        //打印日志的范围,HEADERS、BODY等
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(loggingInterceptor);
        httpClientBuilder.addNetworkInterceptor(mRewriteCacheControlInterceptor);

        Cache cache = new Cache(new File(BaseApplication.mApplicationContext.getExternalCacheDir(), "ExampleHttpCache"), 1024 * 1024 * 100);
        httpClientBuilder.cache(cache);
        httpClientBuilder.retryOnConnectionFailure(true);
        httpClientBuilder.connectTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(Constant.DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                //配置自定义的Convert
                //.addConverterFactory(ResponseConvertFactory.create(new Gson()))
                //配置默认的Convert
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

        @Override
        public T apply(HttpResult<T> httpResult) {
            String respCode = httpResult.code;
            if (!StringUtils.isEmpty(respCode)) {
                if (!respCode.equals(Constant.CODE_RESPONSE_SUCCEED)) {
                    throw new ApiException(respCode, httpResult.code, httpResult.data);
                } else {
                    return httpResult.data;
                }
            }
            return null;
        }
    }


    private <T> void toSubscribe(Observable<T> o, Observer<T> s, LifecycleOwner lifecycleOwner) {
        o.subscribeOn(Schedulers.io())
                //unsubscribeOn?
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //自动管理生命周期
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)))
                .subscribe(s);
    }

    @NonNull
    private RequestBody getBody(Object o) {
        if (Constant.ISSHOWLOG) {
            Logger.d(o);
        }
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(o));
    }


    /**
     * 单个请求
     *
     * @param netObserver
     * @param loginReq
     */
    public void login(NetObserver<LoginRes> netObserver, LoginReq loginReq, LifecycleOwner lifecycleOwner) {
        Observable<LoginRes> observable = mApiService.login(getBody(loginReq)).map(new HttpResultFunc<>());
        toSubscribe(observable, netObserver, lifecycleOwner);
    }


    /**
     * 多个请求合并
     *
     * @param netObserver
     * @param getDictionaryDatReq
     * @param getProductsByTypeReq
     */
    public void addJJMerge(NetObserver<AddJJMergeRes> netObserver, GetDictionaryDatReq getDictionaryDatReq, GetProductsByTypeReq getProductsByTypeReq, LifecycleOwner lifecycleOwner) {
        Observable<ArrayList<GetDictionaryDatRes>> map = mApiService.GetDictionaryDat(getBody(getDictionaryDatReq)).map(new HttpResultFunc<>());
        Observable<ArrayList<GetProductsByTypeRes>> map1 = mApiService.GetProductsByType(getBody(getProductsByTypeReq)).map(new HttpResultFunc<>());
        Observable<ArrayList<GetSaleManListRes>> map2 = mApiService.GetSaleManList().map(new HttpResultFunc<>());
        Observable<AddJJMergeRes> zip = Observable
                .zip(map, map1, map2, (getDictionaryDatRes, getProductsByTypeRes, getSaleManListRes) -> {
                    AddJJMergeRes addJJMergeRes = new AddJJMergeRes();
                    addJJMergeRes.productTypeList = getDictionaryDatRes;
                    addJJMergeRes.productNameList = getProductsByTypeRes;
                    addJJMergeRes.salesManList = getSaleManListRes;
                    return addJJMergeRes;
                });
        toSubscribe(zip, netObserver, lifecycleOwner);
    }

}
