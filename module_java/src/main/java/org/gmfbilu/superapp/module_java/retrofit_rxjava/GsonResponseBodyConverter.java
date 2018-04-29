package org.gmfbilu.superapp.module_java.retrofit_rxjava;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import org.gmfbilu.superapp.lib_base.app.Constant;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;



class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult httpResult = gson.fromJson(response, HttpResult.class);
        String respCode = httpResult.RespCode;
        Logger.json(response);
        if (!respCode.equals(Constant.CODE_RESPONSE_SUCCEED)) {
            throw new ApiException(respCode, httpResult.RespDesc,httpResult.RespData);
        }
        return gson.fromJson(response, type);
    }
}
