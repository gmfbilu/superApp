package org.gmfbilu.superapp.module_view.kLine.MPChart;

import com.chad.library.adapter.base.entity.SectionEntity;


public class WuDangBean  extends SectionEntity<WuDang> {


    public WuDangBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public WuDangBean(WuDang t) {
        super(t);
    }

    @Override
    public String toString() {
        return "WuDangBean{" +
                "isHeader=" + isHeader +
                ", t=" + t +
                ", header='" + header + '\'' +
                '}';
    }
}
