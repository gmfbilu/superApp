package org.gmfbilu.superapp;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by gmfbilu on 2018/3/2.
 */

public class SuperApplication extends TinkerApplication {


    public SuperApplication() {
        //参数1：tinkerFlags 表示Tinker支持的类型 dex only、library only or all suuport，default: TINKER_ENABLE_ALL
        //参数2：delegateClassName Application代理类 这里填写你自定义的ApplicationLike
        //参数3：delegateClassName Application代理类 这里填写你自定义的ApplicationLike
        //参数4：tinkerLoadVerifyFlag 加载dex或者lib是否验证md5，默认为false
        super(ShareConstants.TINKER_ENABLE_ALL,
                "org.gmfbilu.superapp.ApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader",
                false);
    }


}
