package org.gmfbilu.superapp.lib_base.base.mvp;


import androidx.annotation.NonNull;

public interface BaseDelegate<V extends BaseView, P extends BasePresenter<V>>  {

    /**初始化presenter*/
    @NonNull
    P createPresenter();

    /**获取presenter*/
    @NonNull
    P getPresenter();
}
