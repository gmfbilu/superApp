package org.gmfbilu.superapp.lib_base.bean.response;

import java.util.ArrayList;

public class AddJJMergeRes {

    public ArrayList<GetDictionaryDatRes> productTypeList;
    public ArrayList<GetProductsByTypeRes> productNameList;
    public ArrayList<GetSaleManListRes> salesManList;


    @Override
    public String toString() {
        return "AddJJMergeRes{" +
                "productTypeList=" + productTypeList +
                ", productNameList=" + productNameList +
                ", salesManList=" + salesManList +
                '}';
    }
}
