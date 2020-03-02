package org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.entity;

import java.util.Date;

/**
 * 分时图实体接口
 */

public interface IMinuteLine {

    /**
     * @return 获取均价
     */
    float getAvgPrice();

    /**
     * @return 获取成交价
     */
    float getPrice();

    /**
     * 该指标对应的时间
     */
    Date getDate();

    /**
     * 成交量
     */
    float getVolume();
}
