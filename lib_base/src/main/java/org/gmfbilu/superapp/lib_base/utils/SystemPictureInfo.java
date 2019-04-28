package org.gmfbilu.superapp.lib_base.utils;

/**
 * @author Created by zhuNian
 * desc: 系统相册照片信息
 * time:  2019/4/24
 * @Copyright:
 */
public class SystemPictureInfo {

    public String picName;
    public String picPath;
    public String picShoot;
    public String picLatitude;
    public String picLongitude;

    @Override
    public String toString() {
        return "SystemPictureInfo{" +
                "picName='" + picName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", picShoot='" + picShoot + '\'' +
                ", picLatitude='" + picLatitude + '\'' +
                ", picLongitude='" + picLongitude + '\'' +
                '}';
    }
}
