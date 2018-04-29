package org.gmfbilu.superapp.module_util.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 若AIDL文件中涉及到的所有数据类型均为默认支持的数据类型，则无此序列化。因为默认支持的那些数据类型都是可序列化的
 *
 * 这里有一个坑：默认生成的模板类的对象只支持为 in 的定向 tag 。为什么呢？因为默认生成的类里面只有 writeToParcel() 方法，而如果要支持为 out 或者 inout 的定向 tag 的话，还需要实现 readFromParcel() 方法——而这个方法其实并没有在 Parcelable 接口里面，所以需要我们从头写
 */
public class Book implements Parcelable {
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        name = dest.readString();
    }
}
