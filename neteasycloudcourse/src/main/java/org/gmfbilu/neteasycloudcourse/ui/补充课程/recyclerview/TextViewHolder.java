package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.view.View;
import android.widget.TextView;

import org.gmfbilu.neteasycloudcourse.R;

import androidx.annotation.NonNull;

public class TextViewHolder extends MessageViewHolder {

    public TextView text;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        text = itemView.findViewById(R.id.tv_text);
    }

}
