package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.section;


import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * 实体类必须继承SectionEntity
 * 真正的数据实体类是Video
 */
public class SectionBean extends SectionEntity<Video> {


    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionBean(Video t) {
        super(t);
    }

    @Override
    public String toString() {
        return "SectionBean{" +
                "isHeader=" + isHeader +
                ", t=" + t +
                ", header='" + header + '\'' +
                '}';
    }
}
