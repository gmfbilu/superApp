package org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.entity;

/**
 * 布林线指标接口
 * @see <a href="https://baike.baidu.com/item/%E5%B8%83%E6%9E%97%E7%BA%BF%E6%8C%87%E6%A0%87/3325894"/>相关说明</a>
 */

public interface IBOLL {

    /**
     * 上轨线
     */
    float getUp();

    /**
     * 中轨线
     */
    float getMb();

    /**
     * 下轨线
     */
    float getDn();
}
