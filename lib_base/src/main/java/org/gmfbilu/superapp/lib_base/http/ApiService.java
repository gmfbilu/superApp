package org.gmfbilu.superapp.lib_base.http;


import org.gmfbilu.superapp.lib_base.app.Constant;
import org.gmfbilu.superapp.lib_base.bean.response.GetDictionaryDatRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetProductsByTypeRes;
import org.gmfbilu.superapp.lib_base.bean.response.GetSaleManListRes;
import org.gmfbilu.superapp.lib_base.bean.response.LoginRes;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {


    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_LOGIN)
    Observable<HttpResult<LoginRes>> login(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETDICTIONARYDAT)
    Observable<HttpResult<ArrayList<GetDictionaryDatRes>>> GetDictionaryDat(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETPRODUCTSBYTYPE)
    Observable<HttpResult<ArrayList<GetProductsByTypeRes>>> GetProductsByType(@Body RequestBody route);

    @POST(Constant.URL_CONNECTED_MOBILE + Constant.URL_GETSALEMANLIST)
    Observable<HttpResult<ArrayList<GetSaleManListRes>>> GetSaleManList();


}
