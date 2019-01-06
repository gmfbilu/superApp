package org.gmfbilu.superapp.lib_base.view.dialogFragment;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class DialogFragmentViewConvertListener implements Parcelable {

    protected abstract void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public DialogFragmentViewConvertListener() {
    }

    protected DialogFragmentViewConvertListener(Parcel in) {
    }

    public static final Creator<DialogFragmentViewConvertListener> CREATOR = new Creator<DialogFragmentViewConvertListener>() {
        @Override
        public DialogFragmentViewConvertListener createFromParcel(Parcel source) {
            return new DialogFragmentViewConvertListener(source){
                @Override
                protected void convertView(DialogFragmentViewHolder holder, BaseDialogFragment dialog) {

                }
            };
        }

        @Override
        public DialogFragmentViewConvertListener[] newArray(int size) {
            return new DialogFragmentViewConvertListener[size];
        }
    };
}
