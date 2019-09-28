package org.gmfbilu.superapp.lib_base.http;


import com.google.gson.Gson;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;
import org.gmfbilu.superapp.lib_base.utils.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 拦截器可以注册为应用拦截器和网络拦截器
 * <p>
 * 应用拦截器：
 * 1.不需要关心像重定向和重试这样的中间响应
 * 2.即使调用一次，即使HTTP响应从缓存中获取服务
 * 3.监视应用原始意图。不关心OkHttp注入的像If-None-Match头
 * 4.允许短路并不调用Chain.proceed()
 * 5.允许重试并执行多个Chain.proceed()调用
 * <p>
 * 应用拦截器：
 * 1.可以操作像重定向和重试这样的中间响应
 * 2.对于短路网络的缓存响应不会调用
 * 3.监视即将要通过网络传输的数据
 * 4.访问运输请求的Connection
 * <p>
 * <p>
 * <p>
 * 一般自定义拦截器主要分三步,下一步和上一步是一个异步过程
 * 1.修改请求消息
 * 2.调用拦截器链获取结果
 * 3.修改响应
 */
public class EncryptInterceptor implements Interceptor {


    private static final Charset UTF8 = Charset.forName("UTF-8");

    private Buffer buffer = new Buffer();

    public static String requestBodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
            return chain.proceed(originalRequest);
        }
        HttpUrl url = originalRequest.url();
        //http://127.0.0.1/test/upload/img?userName=xiaoming&userPassword=12345
        String scheme = url.scheme();//  http 或 https
        String host = url.host();//   127.0.0.1 或 www.google.com
        String path = url.encodedPath();//  /test/upload/img
        String query = url.encodedQuery();//  userName=xiaoming&userPassword=12345 ，如果是post请求则是null
        Headers headers = originalRequest.headers();
        if (headers != null) {
            Set<String> names = headers.names();
            if (headers.size() == 0) {
                LoggerUtil.d("没有 Headers");
            }
            for (String head : names) {
                LoggerUtil.d(head);
            }
        } else {
            LoggerUtil.d(" Headers 为 null");
        }

        //一般POST请求参数都是放在RequestBody中，拿到数据后可以对数据做手脚
        RequestBody body = originalRequest.body();
        if (body != null) {
            if (body instanceof FormBody) {
                //Content-Type是application/x-www-form-urlencoded 表单提交数据
                FormBody formBody = (FormBody) body;
                for (int i = 0; i < formBody.size(); i++) {
                    //一般情况下encodedName和name一致，但是在中文情况下encodedValue可能乱码
                    //encodedName对应addEncoded
                    String encodedName = formBody.encodedName(i);
                    String encodedValue = formBody.encodedValue(i);
                    String name = formBody.name(i);
                    String value = formBody.value(i);
                    LoggerUtil.d(" encodedName =" + encodedName + ", encodedValue=" + encodedValue + ", name=" + name + ", value=" + value);
                }
            } else if (body instanceof MultipartBody) {
                //Content-Type是x-www-form-urlencoded 复杂数据格式
                MultipartBody multipartBody = (MultipartBody) body;
                List<MultipartBody.Part> parts = multipartBody.parts();
                for (int i = 0; i < parts.size(); i++) {
                    MultipartBody.Part part = parts.get(i);
                    RequestBody requestBody = part.body();
                    //当表单提交的时候只能得到值
                    String requestBodyString = requestBodyToString(requestBody);
                    LoggerUtil.d(" requestBodyString =" + requestBodyString);
                }
            } else {
                //Content-Type是application/json，数据是个json {"password":"ertt","username":"的发发发"}
                //数据是个json {"password":"ertt","username":"的发发发"}，也有可能是organId=1002&username=嗯&password=2
                String bodyToString = requestBodyToString(body);
                if (StringUtils.isEmpty(bodyToString)) {
                    LoggerUtil.d(" RequestBody 没有数据");
                } else {
                    LoggerUtil.d(bodyToString);
                    if (bodyToString.contains("{") && bodyToString.contains("}")) {
                        try {
                            JSONObject object = new JSONObject(bodyToString);
                            Iterator<String> keys = object.keys();
                            HashMap<String, String> stringStringHashMap = new HashMap<>();
                            while (keys.hasNext()) {
                                String key = keys.next();
                                String value = object.getString(key);
                                LoggerUtil.d(" key =" + key + ", value=" + value);
                                stringStringHashMap.put(key, value + "加密");
                            }
                            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(stringStringHashMap));
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    } else if (bodyToString.contains("=")) {
                        String[] split = bodyToString.split("&");
                        for (int i = 0; i < split.length; i++) {
                            String[] keyValue = split[i].split("=");
                            String key = keyValue[0];
                            String value = keyValue[1];
                            LoggerUtil.d("key=" + key + ", value=" + value);
                        }
                    }
                }
            }
        } else {
            LoggerUtil.d(" RequestBody 为 null");
        }


        Map<String, String> httpUrlParams = getHttpUrlParams(originalRequest.url());
        if (httpUrlParams == null) {
            LoggerUtil.d(" httpUrlParams 为 null");
        } else {
            int size = httpUrlParams.size();
            LoggerUtil.d(" httpUrlParams 数据为" + size + "个");
            for (Map.Entry<String, String> entry : httpUrlParams.entrySet()) {
                String mapKey = entry.getKey();
                String mapValue = entry.getValue();
                LoggerUtil.d(" mapKey =" + mapKey + " , mapValue=" + mapValue);

            }
        }


        //1.修改请求消息
        Request newRequest = originalRequest.newBuilder()
                //.header("Content-Type", "application/json;charset=utf-8")//header必须是已经存在的header?
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")//header必须是已经存在的header?
                .method(originalRequest.method(), originalRequest.body())
                .build();
        //2.调用拦截器链获取结果
        Response originalResponse = chain.proceed(newRequest);
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        MediaType contentType = responseBody.contentType();
        String responseBodyString = buffer.clone().readString(UTF8);
        LoggerUtil.d(responseBodyString);//{"code":"1","message":"用户名或密码错误，已错误1次，连续错误5次会锁定用户"}

        //3.修改响应。重写响应头并且改变响应主体，这个通常是比重写请求头更危险的，因为它可能违背网络服务器的期望
        Response newResponse = originalResponse.newBuilder()
                .build();
        return newResponse;
    }


    /**
     * get请求中有数据？
     * post请求是没有的
     * 获取url中的参数
     *
     * @param httpUrl
     * @return
     */
    private Map<String, String> getHttpUrlParams(HttpUrl httpUrl) {
        Set<String> paramsSet = httpUrl.queryParameterNames();
        Map<String, String> paramMap = new HashMap<>();
        if (paramsSet != null && paramsSet.size() > 0) {
            for (String s : paramsSet) {
                paramMap.put(s, httpUrl.queryParameter(s));
            }
        }
        return paramMap;
    }
}
