package org.gmfbilu.superapp.module_util.aop.埋点;

import org.gmfbilu.superapp.lib_base.utils.AppUtils;

/**
 * 假设上传的埋点json
 */
public class DataCollectionReq {

    /**
     * 系统（Android/iOS）
     */
    private String OS = "Android";
    /**
     * 机型
     */
    private String deviceType ;
    /**
     * 系统版本
     */
    private String OSversion = AppUtils.getDeviceVersionRelease();
    /**
     * 设备码
     */
    private String deviceCode;
    /**
     * 产品版本
     */
    private String versionCode = AppUtils.getAppVersion();
    /**
     * 埋点类型（0—页面访问，1—按钮点击，2—访问时长，3—查看产品打开，4—产品下载安装）
     */
    public String type;
    /**
     * 埋点值
     */
    public String typeValue;
    /**
     *备用字段（访问时长等）
     */
    /**
     * 用户ID
     */
    public String userID;
    /**
     * 打开时间（不同的产品打开场景，后端打开、杀掉进程打开、手机休眠再打开）
     */
    /**
     *操作时间
     */

    @Override
    public String toString() {
        return "DataCollectionReq{" +
                "OS='" + OS + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", OSversion='" + OSversion + '\'' +
                ", deviceCode='" + deviceCode + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", type='" + type + '\'' +
                ", typeValue='" + typeValue + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
