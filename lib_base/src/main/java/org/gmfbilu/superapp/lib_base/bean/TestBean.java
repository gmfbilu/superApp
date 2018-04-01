package org.gmfbilu.superapp.lib_base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gmfbilu on 2018/3/7.
 */

public class TestBean implements Parcelable {

    public long id;
    public String name;

    public TestBean(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public TestBean() {
    }

    protected TestBean(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<TestBean> CREATOR = new Creator<TestBean>() {
        @Override
        public TestBean createFromParcel(Parcel source) {
            return new TestBean(source);
        }

        @Override
        public TestBean[] newArray(int size) {
            return new TestBean[size];
        }
    };
}
