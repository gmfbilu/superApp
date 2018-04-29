package org.gmfbilu.superapp.module_java.retrofit_rxjava;


public class HttpResult<T> {
    public T RespData;
    public String RespCode;
    public String RespDesc;

    @Override
    public String toString() {
        return "HttpResult{" +
                "RespData=" + RespData +
                ", RespCode='" + RespCode + '\'' +
                ", RespDesc='" + RespDesc + '\'' +
                '}';
    }
}