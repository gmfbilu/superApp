package org.gmfbilu.superapp.module_java.rxJava;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        //修改请求信息
        Request newRequest = originalRequest.newBuilder().addHeader("XX","XX").tag("").build();
        //调用拦截器获取处理结果
        Response originalResponse = chain.proceed(newRequest);
        //拿到结果进行处理后返回
        Response newResponse = originalResponse.newBuilder().removeHeader("XX").build();
        //RealInterceptorChain//是Chain唯一的实现类
        return newResponse;
    }
}
