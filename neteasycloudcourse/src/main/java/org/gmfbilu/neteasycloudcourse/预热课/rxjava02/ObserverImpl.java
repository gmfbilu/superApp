package org.gmfbilu.neteasycloudcourse.预热课.rxjava02;

import org.gmfbilu.superapp.lib_base.utils.LoggerUtil;

public class ObserverImpl implements Observer {


    @Override
    public void change(Object object) {
        LoggerUtil.d(object);
    }
}
