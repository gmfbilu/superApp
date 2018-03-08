package org.gmfbilu.lib_base.base;

import android.app.Application;

import org.gmfbilu.lib_base.utils.Utils;

/**
 * Created by gmfbilu on 2018/3/2.
 * <p>
 * 组件中获取Context的方法必须为:Utils.getContext()，不允许其他写法；
 * 要想使用BaseApplication，必须在组件中实现自己的Application，并且继承BaseApplication；
 * 组件中实现的Application必须在debug包中的AndroidManifest.xml中注册，否则无法使用；
 * 组件的Application需置于java/debug文件夹中，不得放于主代码；
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }


}
