package org.gmfbilu.superapp.module_util;

import org.gmfbilu.superapp.lib_base.base.mvp.BasePresenter;

public interface MainPresenter extends BasePresenter<MainView> {

    void signInP(String httpResult);
}
