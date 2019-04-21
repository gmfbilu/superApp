package org.gmfbilu.superapp.module_util.aop.埋点;


/**
 * 数据埋点key
 */
public interface DataCollectionTag {

    String METHOD_CLICK="method_click";
    String METHOD_ONCREATE="method_oncreate";
    String METHOD_ONRESUME="method_onresume";
    String METHOD_OPAUSE="method_onpause";

    /**
     * 埋点类型
     */
    String TYPE_PAGE="0";
    String TYPE_CLICK="1";

    /**
     * 微信登录点击事件
     */
    String CLICK_WECHART_LOGIN = "weChartLogin";

    /**
     * qq登录点击事件
     */
    String CLICK_QQ_LOGIN = "qqLogin";

    /**
     * 登录页面
     */
    String PAGE_LOGIN = "loginPage";
}
