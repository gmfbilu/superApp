package org.gmfbilu.superapp.module_view.fragment.module.firstTab;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gmfbilu on 18-3-11.
 */

public class Message implements Parcelable {

    public String mesage;

    public Message(String mesage) {
        this.mesage = mesage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mesage);
    }

    protected Message(Parcel in) {
        this.mesage = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
