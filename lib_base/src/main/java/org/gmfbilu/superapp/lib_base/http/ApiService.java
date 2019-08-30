package org.gmfbilu.superapp.lib_base.http;


import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.bean.response.FileRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetDictionaryDatRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetProductsByTypeRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetSaleManListRes;
import org.gmfbilu.superapp.lib_base.bean.response.LoginRes;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {


    //RequestBody 有请求参数的
    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_LOGIN)
    Observable<HttpResult<LoginRes>> login(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETDICTIONARYDAT)
    Observable<HttpResult<ArrayList<GetDictionaryDatRes>>> GetDictionaryDat(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETPRODUCTSBYTYPE)
    Observable<HttpResult<ArrayList<GetProductsByTypeRes>>> GetProductsByType(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETSALEMANLIST)
    Observable<HttpResult<ArrayList<GetSaleManListRes>>> GetSaleManList();

    //上传文件
    //没有使用@Post（“地址”）方法，使用了动态的url，方便封装
  /*  @Multipart
    @POST
    Observable<HttpResult<FileRes>> uploadFile(@Url String url, @Part MultipartBody.Part file);*/

    @Multipart
    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_UPLOADFILE)
    Observable<HttpResult<FileRes>> uploadFile(@Part MultipartBody.Part file);



}
