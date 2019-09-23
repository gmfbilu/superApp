package org.gmfbilu.superapp.lib_base.base.mvp;

public abstract class BaseModel<IP> {

    protected IP mPresenter;

    public BaseModel(IP iPresenter) {
        this.mPresenter = iPresenter;
    }



}