package org.gmfbilu.superapp.module_view.recyclerView.baseRecyclerViewAdapter.multipleItem;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 实体类必须实现MultiItemEntity，在设置数据的时候，需要给每一个数据设置itemType
 */
public class MultipleItem implements MultiItemEntity {

    public static final int TEXT = 1;
    public static final int IMG = 2;
    private int itemType;

    public MultipleItem(int itemType) {
        this.itemType = itemType;
    }


    @Override
    public int getItemType() {
        return itemType;
    }

    public String name;
    public String age;
    public String picUrl;
    public String picDes;

    @Override
    public String toString() {
        return "MultipleItem{" +
                "itemType=" + itemType +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", picDes='" + picDes + '\'' +
                '}';
    }
}
