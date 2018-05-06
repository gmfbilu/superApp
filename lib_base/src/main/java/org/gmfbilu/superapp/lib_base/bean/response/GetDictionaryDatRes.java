package org.gmfbilu.superapp.lib_base.bean.response;

public class GetDictionaryDatRes {

    public String code;
    public int enum_key;
    public String key;
    public String value;

    @Override
    public String toString() {
        return "GetDictionaryDatRes{" +
                "code='" + code + '\'' +
                ", enum_key=" + enum_key +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
