package org.gmfbilu.superapp.lib_base.base.mvp;



public interface BasePresenter<V extends BaseView>{

    /**
     * 绑定接口
     */
    void attachView(V view);

    /**
     * 释放接口
     */
    void detachView();


}
