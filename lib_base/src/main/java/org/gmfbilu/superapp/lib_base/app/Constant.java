package org.gmfbilu.superapp.lib_base.app;

public interface Constant {

    boolean ISRELEASE = false;
    boolean ISSHOWLOG = true;
    String BASE_PACKAGENAME = "org.gmfbilu.superapp";
    String BASE_CHANNEL = "superapp";
    String LOG_NAME = "SuperApp";
    String BUGLY_APPID = "e5ab76a7fa";
    String PUSH_APPKEY = "5ad82ca4b27b0a26d7000111";
    String PUSH_SECRET = "7686f7b0654165d11e8c779f70c575db";
    String LINE_SEPARATOR = "\n";



    //========================================================================
    String CODE_RESPONSE_SUCCEED = "1000";
    int DEFAULT_TIMEOUT = 30;
    String BASE_URL = "http://114.55.27.208:8765/";
    String URL_CONNECTED_MOBILE = "Api/";
    String URL_LOGIN = "User/UserLogin";
    //新增进件-获取产品类型
    String URL_GETDICTIONARYDAT = "Dictionary/GetDictionaryData";
    //新增进件-获取产品名称
    String URL_GETPRODUCTSBYTYPE = "Product/GetProductsByType";
    //新增进件-获取业务员
    String URL_GETSALEMANLIST = "user/GetSaleManList";
}
