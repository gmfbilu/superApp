package org.gmfbilu.superapp.lib_base.http;


public class HttpResult<T> {
    public T data;
    public String code;
    public String message;

    @Override
    public String toString() {
        return "HttpResult{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}