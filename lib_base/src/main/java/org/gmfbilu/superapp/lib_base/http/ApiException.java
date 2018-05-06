package org.gmfbilu.superapp.lib_base.http;

import android.os.Parcel;
import android.os.Parcelable;


public class ApiException extends RuntimeException implements Parcelable {

    public String ResponseCodeError;
    public String ResponseMessageError;
    public Object RespData;

    public ApiException(String errorCode, String errorMessage, Object o) {
        super();
        this.ResponseCodeError = errorCode;
        this.ResponseMessageError = errorMessage;
        this.RespData = o;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ResponseCodeError);
        dest.writeString(this.ResponseMessageError);
    }

    protected ApiException(Parcel in) {
        this.ResponseCodeError = in.readString();
        this.ResponseMessageError = in.readString();
    }

    public static final Creator<ApiException> CREATOR = new Creator<ApiException>() {
        @Override
        public ApiException createFromParcel(Parcel source) {
            return new ApiException(source);
        }

        @Override
        public ApiException[] newArray(int size) {
            return new ApiException[size];
        }
    };

    @Override
    public String toString() {
        return "ApiException{" +
                "ResponseCodeError='" + ResponseCodeError + '\'' +
                ", ResponseMessageError='" + ResponseMessageError + '\'' +
                ", RespData=" + RespData +
                '}';
    }
}
