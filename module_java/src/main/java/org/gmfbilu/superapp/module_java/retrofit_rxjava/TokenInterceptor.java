package org.gmfbilu.superapp.module_java.retrofit_rxjava;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * 判断Token是否过期的拦截器
 */

public class TokenInterceptor implements Interceptor {

    private static final String TAG = "TokenInterceptor";

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // try the request
        Response originalResponse = chain.proceed(request);

        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        String bodyString = buffer.clone().readString(charset);
        com.orhanobut.logger.Logger.d("Singlee body--->" + request.url().toString() + "    " + bodyString);
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            int errorCode = jsonObject.getInt("code");
      /*      if (StatusCode.isInvalidToken(errorCode)) {
                // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
                HttpResult<AccessToken> result = ApiWrapper.getInstance().getToken();
                if (StatusCode.isSuccessful(result.code)) {
                    AccessToken accessToken = result.data;
                    String newToken = accessToken.getAccess_token();
                    com.orhanobut.logger.Logger.d("Singlee newToken----->" + newToken);
                    // create a new request and modify it accordingly using the new token
                    Request newRequest = request.newBuilder().header("token", newToken).build();
                    // retry the request
                    originalResponse.body().close();
                    return chain.proceed(newRequest);
                }
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // otherwise just pass the original response on
        return originalResponse;
    }
}

