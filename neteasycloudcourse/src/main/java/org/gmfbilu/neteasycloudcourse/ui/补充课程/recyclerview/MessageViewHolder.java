package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.view.View;
import android.widget.TextView;

import org.gmfbilu.neteasycloudcourse.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView time;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        time=itemView.findViewById(R.id.tv_time);
    }


}
