package org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.formatter;


import org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.DateUtil;
import org.gmfbilu.superapp.module_view.kLine.tifezh.kchartlib.base.IDateTimeFormatter;

import java.util.Date;

/**
 * 时间格式化器
 * Created by tifezh on 2016/6/21.
 */

public class TimeFormatter implements IDateTimeFormatter {
    @Override
    public String format(Date date) {
        if (date == null) {
            return "";
        }
        return DateUtil.shortTimeFormat.format(date);
    }
}
